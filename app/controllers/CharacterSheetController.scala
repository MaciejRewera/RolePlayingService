package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import views.html.character_sheet

import scala.concurrent.Future

@Singleton
class CharacterSheetController @Inject()(
  cc: ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  def displayPage(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(character_sheet()))
  }

}
