package controllers;

import actors.MyWebSocketActor;
import akka.stream.javadsl.Flow;
import play.api.libs.streams.ActorFlow;
import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public LegacyWebSocket<String> ws() {
        return WebSocket.withActor(MyWebSocketActor::props);
    }

    public Result wspage() {
        return ok(wspage.render());
    }

    /* AkkaStream Java API is not ready yet!!!
    public WebSocket<String> wsnew() {
        return WebSocket.Text.accept(requestHeader -> ActorFlow.actorRef(MyWebSocketActor::props));
    }
    */

}
