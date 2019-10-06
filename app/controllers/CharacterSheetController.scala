package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repositories.CharacterStatsRepository
import views.html.character_sheet

import scala.concurrent.ExecutionContext

@Singleton
class CharacterSheetController @Inject()(
  cc: ControllerComponents,
  characterStatsRepo: CharacterStatsRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def displayPage(): Action[AnyContent] = Action.async { implicit request =>
    characterStatsRepo.find("id" -> "09cd7474-bdf9-49e6-9f99-e8b89fd2cacd").map { statsList =>
      Ok(character_sheet(statsList.head))
    }
  }

}
