package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repositories.CharacterStatsRepository
import views.html.character_sheet

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class CharacterSheetController @Inject()(cc: ControllerComponents, characterStatsRepo: CharacterStatsRepository)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  def displayPage(sheetId: String): Action[AnyContent] = Action.async { implicit request =>
    characterStatsRepo.find("id" -> sheetId).map { statsList =>
      Ok(character_sheet(statsList.head))
    }
  }

}
