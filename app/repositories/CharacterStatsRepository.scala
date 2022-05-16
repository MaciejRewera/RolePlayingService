package repositories

import models.charactersheet.CharacterStats
import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.json.Json.JsValueWrapper
import reactivemongo.api.Cursor.FailOnError
import reactivemongo.api.commands.{WriteConcern, WriteResult}
import reactivemongo.api.indexes.{Index, IndexType}
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver, ReadPreference}
import reactivemongo.core.errors.GenericDatabaseException
import reactivemongo.play.json.ImplicitBSONHandlers.JsObjectDocumentWriter
import reactivemongo.play.json.collection.JSONCollection

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CharacterStatsRepository @Inject()(implicit ec: ExecutionContext) {

  private val logger = Logger(this.getClass)
  private val mongoUri = "mongodb://localhost:27018/role-playing-service"

  private val driver = new MongoDriver

  private val database: Future[DefaultDB] = for {
    uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
    connection <- Future.fromTry(driver.connection(uri, strictUri = false))
    db <- connection.database(uri.db.get)
  } yield db

  private lazy val collection: Future[JSONCollection] = database.map(_.collection("character-stats"))

  def indexes: Seq[Index] = Seq(Index(Seq("id" -> IndexType.Ascending), name = Some("idIdx"), unique = true))

  def ensureIndexes(implicit ec: ExecutionContext): Future[Seq[Boolean]] =
    Future.sequence(indexes.map(ensureIndex))

  private val message: String = "Failed to ensure index"

  private def ensureIndex(index: Index)(implicit ec: ExecutionContext): Future[Boolean] =
    collection
      .flatMap(_.indexesManager.create(index))
      .map(wr => {
        if (!wr.ok) {
          val msg = wr.writeErrors.mkString(", ")
          val maybeMsg = if (msg.contains("E11000")) {
            // this is for backwards compatibility to mongodb 2.6.x
            throw GenericDatabaseException(msg, wr.code)
          } else Some(msg)
          logger.error(s"$message (${index.eventualName}) : '${maybeMsg.map(_.toString)}'")
        }
        wr.ok
      })
      .recover {
        case t =>
          logger.error(s"$message (${index.eventualName})", t)
          false
      }

  def create(stats: CharacterStats): Future[WriteResult] = collection.flatMap(_.insert.one[CharacterStats](stats))

  def find(query: (String, JsValueWrapper)*)(implicit ec: ExecutionContext): Future[List[CharacterStats]] =
    collection.flatMap(
      _.find(Json.obj(query: _*), None)
        .cursor[CharacterStats](ReadPreference.primaryPreferred)
        .collect(maxDocs = -1, FailOnError[List[CharacterStats]]())
    )

  def findById(sheetId: UUID)(implicit ec: ExecutionContext): Future[Option[CharacterStats]] =
    find("id" -> sheetId.toString).map(_.headOption)

  def findAll(
    readPreference: ReadPreference = ReadPreference.primaryPreferred
  )(implicit ec: ExecutionContext): Future[List[CharacterStats]] =
    collection.flatMap(
      _.find(Json.obj(), None)
        .cursor[CharacterStats](readPreference)
        .collect(maxDocs = -1, FailOnError[List[CharacterStats]]())
    )

  def remove(query: (String, JsValueWrapper)*)(implicit ec: ExecutionContext): Future[WriteResult] =
    collection.flatMap(_.delete(writeConcern = WriteConcern.Default).one(Json.obj(query: _*)))

  def removeAll(writeConcern: WriteConcern = WriteConcern.Default)(implicit ec: ExecutionContext): Future[WriteResult] =
    collection.flatMap(_.delete(ordered = true, writeConcern).one(Json.obj(), None))

}
