package controllers

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repositories.CharacterStatsRepository

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class BackendController @Inject()(cc: ControllerComponents, characterStatsRepository: CharacterStatsRepository, )(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  def getCharacterSheet(sheetId: String): Action[AnyContent] = Action.async { _ =>
    characterStatsRepository.findById(UUID.fromString(sheetId)).map {
      case Some(stats) => Ok(Json.toJson(stats))
      case None        => NotFound
    }
  }

  def getAllCharacterSheets(): Action[AnyContent] = Action.async { _ =>
    characterStatsRepository.findAll().map { statsList =>
      Ok(Json.toJson(statsList))
    }
  }

}
