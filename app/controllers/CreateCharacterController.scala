package controllers

import forms.InitialAttributes
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import views.html.create_attributes

import scala.concurrent.Future

@Singleton
class CreateCharacterController @Inject()(
  cc: ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  import scala.concurrent.ExecutionContext.Implicits.global
  private val logger = Logger(this.getClass)

  private def collection: Future[JSONCollection] =
    database.map(_.collection[JSONCollection]("attributes"))

  def displayPage(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(create_attributes(InitialAttributes.form())))
  }

  def submitForm(): Action[AnyContent] = Action.async { implicit request =>
    InitialAttributes.form().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(create_attributes(formWithErrors))),
      attributes => {
        onSuccessfulValidation(attributes)
        Future.successful(Redirect(routes.HomeController.index()))
      }
    )
  }

  private def onSuccessfulValidation(initialAttributes: InitialAttributes): Unit = {
//    val characteristics = Characteristics(initialAttributes)
//    println("Now application should write to the DB with: " + characteristics)
//
//    collection.flatMap(_.insert.one(characteristics)).map { lastError =>
//      logger.debug(s"Successfully inserted with LastError: $lastError")
//    }

  }

}
