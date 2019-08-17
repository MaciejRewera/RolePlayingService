package repositories

import models.charactersheet.CharacterStats
import play.api.libs.json.Json
import play.api.libs.json.Json.JsValueWrapper
import reactivemongo.api.Cursor.FailOnError
import reactivemongo.api.commands.{WriteConcern, WriteResult}
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver, ReadPreference}
import reactivemongo.play.json.ImplicitBSONHandlers.JsObjectDocumentWriter
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

class CharacterStatsRepository(implicit ec: ExecutionContext) {

  private val mongoUri = "mongodb://localhost:27018/role-playing-service"

  private val driver = new MongoDriver

  private val database: Future[DefaultDB] = for {
    uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
    connection <- Future.fromTry(driver.connection(uri, strictUri = false))
    db <- connection.database(uri.db.get)
  } yield db

  private def collection: Future[JSONCollection] = database.map(_.collection("character-stats"))

  def create(stats: CharacterStats): Future[WriteResult] = collection.flatMap(_.insert.one[CharacterStats](stats))

  def find(query: (String, JsValueWrapper)*)(implicit ec: ExecutionContext): Future[List[CharacterStats]] =
    collection.flatMap(
      _.find(Json.obj(query: _*), None)
        .cursor[CharacterStats](ReadPreference.primaryPreferred)
        .collect(maxDocs = -1, FailOnError[List[CharacterStats]]())
    )

  def findAll(readPreference: ReadPreference = ReadPreference.primaryPreferred)(
    implicit ec: ExecutionContext): Future[List[CharacterStats]] =
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
