package controllers

import play.api.Configuration
import play.api.http.HttpErrorHandler
import play.api.mvc._
import repositories.CharacterStatsRepository
import views.html.index

import javax.inject._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  assets: Assets,
  errorHandler: HttpErrorHandler,
  config: Configuration,
  characterStatsRepository: CharacterStatsRepository,
  startPage: index
) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index: Action[AnyContent] = assets.at("index.html")

  /** This makes sense only if all backend endpoints start with this prefix. In production, it is then returned NOT_FOUND.
    * But has no effect in dev mode and even non-existing endpoints return index.html
    */
  def assetOrDefault(resource: String): Action[AnyContent] = if (resource.startsWith(config.get[String]("apiPrefix"))){
    Action.async(r => errorHandler.onClientError(r, NOT_FOUND, "Not found"))
  } else {
    if (resource.contains(".")) assets.at(resource) else index
  }

//    Action.async { implicit request =>
//    characterStatsRepository.findAll().map { sheets =>
//      Ok(startPage(sheets))
//    }
//  }

}
