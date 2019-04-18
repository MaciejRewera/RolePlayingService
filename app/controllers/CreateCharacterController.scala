package controllers

import akka.actor.ActorSystem
import forms.Attributes
import javax.inject.{Inject, Singleton}
import models.charactersheet.characteristics.Characteristics
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import views.html.create_attributes

import scala.concurrent.Future

@Singleton
class CreateCharacterController @Inject()(
  cc: ControllerComponents,
  actorSystem: ActorSystem
) extends AbstractController(cc) {

  def displayPage(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(create_attributes(Attributes.form())))
  }

  def submitForm(): Action[AnyContent] = Action.async { implicit request =>
      Attributes.form().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(create_attributes(formWithErrors))),
      attributes => {
        onSuccessfulValidation(attributes)
        Future.successful(Redirect(routes.HomeController.index()))
      }
    )
  }

  private def onSuccessfulValidation(initialAttributes: Attributes): Unit = {
    val characteristics = Characteristics(initialAttributes)
    println("Now application should write to the DB with: " + characteristics)
  }

}
