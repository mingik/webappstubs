import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.junit.*;

import play.Application;
import play.Configuration;
import play.Environment;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.*;
import play.test.*;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static play.inject.Bindings.bind;
import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertTrue(browser.pageSource().contains("Your new application is ready."));
        });
    }

    @Test
    public void testCRUD() {
        running(testServer(3333), () -> {
            try {
                WSClient ws = WS.newClient(3333);

                // Create
                String id = (new ObjectId()).toHexString();
                CompletableFuture<WSResponse> createResponse = ws.url("/v1/entities/new").post(Json.toJson("{\"age\":16, \"name\":\"entity1\",\"_id\":\"" + id + "\"}")).toCompletableFuture();
                WSResponse wsResponse = createResponse.get();
                assertEquals(CREATED, wsResponse.getStatus());
                assertEquals(id, wsResponse.getHeader("Location"));

                // Read
                CompletableFuture<WSResponse> readResponse = ws.url("/v1/entities/" + id).get().toCompletableFuture();
                wsResponse = readResponse.get(6, TimeUnit.SECONDS);
                ObjectNode readJS = (ObjectNode) wsResponse.asJson();
                assertEquals(readJS.get("age").asInt(), 16);
                assertEquals(readJS.get("name").asText(), "entity1");

                // update
                CompletableFuture<WSResponse> updateResponse = ws.url("/v1/entities/" + id).put(Json.toJson("{\"age\":17, \"name\":\"entity2\"}")).toCompletableFuture();
                wsResponse = updateResponse.get(6, TimeUnit.SECONDS);
                assertEquals(OK, wsResponse.getStatus());

                // delete
                CompletableFuture<WSResponse> deleteResponse = ws.url("/v1/entities/" + id).delete().toCompletableFuture();
                wsResponse = deleteResponse.get(6, TimeUnit.SECONDS);
                assertEquals(OK, wsResponse.getStatus());
            } catch (Exception e) {
                fail("");
            }
        });
    }

}
