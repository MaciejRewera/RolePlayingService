package controllers

import play.api.mvc._
import repositories.CharacterStatsRepository
import views.html.index

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  characterStatsRepository: CharacterStatsRepository,
  startPage: index
) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index: Action[AnyContent] = Action.async { implicit request =>
    characterStatsRepository.findAll().map { sheets =>
      Ok(startPage(sheets))
    }
  }

}
