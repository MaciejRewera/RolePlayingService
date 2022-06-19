package controllers

import forms.InitialAttributes
import models.charactersheet.CharacterStats
import play.api.Logger
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repositories.CharacterStatsRepository
import views.html.create_attributes

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class CreateCharacterController @Inject()(cc: ControllerComponents,
  characterStatsRepository: CharacterStatsRepository)
    extends AbstractController(cc) {

  import scala.concurrent.ExecutionContext.Implicits.global
  private val logger = Logger(this.getClass)


  def displayPage(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(create_attributes(InitialAttributes.form())))
  }

  def submitForm(): Action[AnyContent] = Action.async { implicit request =>
    InitialAttributes
      .form()
      .bindFromRequest()
      .fold(formWithErrors => Future.successful(BadRequest(create_attributes(formWithErrors))), attributes => {
        onSuccessfulValidation(attributes)
        Future.successful(Redirect(routes.HomeController.index()))
      })
  }

  private def onSuccessfulValidation(initialAttributes: InitialAttributes): Unit = {
    val characterStats = CharacterStats(characteristics = initialAttributes.toCharacteristics)
    println("Now application should write to the DB with: " + characterStats)

    characterStatsRepository.create(characterStats).map { result =>
      logger.debug(s"Successfully inserted with WriteResult: $result")
    }
  }

}
