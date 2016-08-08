package actors

import akka.actor.{ActorLogging, Actor, Props, ActorRef}
import play.api.Logger
import play.api.libs.json.JsValue

/**
 * Created by mintik on 8/7/16.
 */
object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor with ActorLogging {
  def receive = {
    case msg: String =>
      out ! ("I received your String message: " + msg)

    case msg  => unhandled(msg)
  }

  override def postStop() = {
    Logger.debug(s"WebSocket is closed, clean resources!")
    //someResource.close()
  }
}
