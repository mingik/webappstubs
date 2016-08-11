package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import scala.util.parsing.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by mintik on 8/7/16.
 */
public class MyWebSocketActor extends UntypedActor {
    public static Props props(ActorRef out) {
        return Props.create(MyWebSocketActor.class, out);
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    private final ActorRef out;

    public MyWebSocketActor(ActorRef out) {
        this.out = out;
    }

    public void onReceive(Object message) throws Exception {

        Logger.info("{}: received message of class {} and string representation {}", self(), message.getClass(), message);

        if (message instanceof String) {
            out.tell("I received your message: " + message, self());

            try {
                JsonNode in = objectMapper.readTree((String) message);

                Logger.info("Parsed as JSON: {}", in);

                if (in instanceof ObjectNode) {
                    ObjectNode objIn = (ObjectNode) in;
                    StringBuffer sb = new StringBuffer();
                    Iterator<String> namesIt = objIn.fieldNames();
                    while (namesIt.hasNext()) {
                        sb.append(namesIt.next() + ":");
                    }
                    out.tell("parsed as JSON object: field names are: " + sb.toString(), self());
                } else if (in instanceof ArrayNode) {
                    ArrayNode arrIn = (ArrayNode) in;
                    Iterator<JsonNode> arrIt = arrIn.elements();
                    int numberOfElements = 0;
                    while (arrIt.hasNext()) {
                        arrIt.next();
                        numberOfElements++;
                    }
                    out.tell("parsed as JSON array: number of elements: " + numberOfElements, self());
                } else {
                    out.tell("don't know", self());
                }
            } catch (Throwable th) {
                Logger.error("Couldn't parse as JSON due to {}", th);
                out.tell("Couldn't parse as JSON", self());
            }
        } else {
            unhandled(message);
        }
    }

    @Override
    public void postStop() throws Exception {
        Logger.error("Was stopped!");
    }
}
