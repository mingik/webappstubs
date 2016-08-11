package controllers

import javax.inject._
import actors.MyWebSocketActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api._
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (implicit system: ActorSystem, materializer: Materializer) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def ws = WebSocket.accept[String, String] { request => ActorFlow.actorRef(out => MyWebSocketActor.props(out))}

  // def ws = WebSocket.accept[JsValue, JsValue] { request => ActorFlow.actorRef(out => MyWebSocketActor.props(out))}

  def wspage = Action { Ok(views.html.wspage()) }
}
