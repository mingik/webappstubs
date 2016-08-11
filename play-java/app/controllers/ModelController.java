package controllers;

import org.bson.Document;
import org.bson.types.ObjectId;
import play.Configuration;
import play.libs.F;
import play.libs.Json;
import play.libs.concurrent.Futures;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.StatusHeader;
import services.MongoService;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by mintik on 8/10/16.
 */
public class ModelController extends Controller {

    private MongoService mongoService;

    @Inject
    public ModelController(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    // id is objectId (or "_id" field)
    public CompletionStage<Result> read(String id) {
        // waits for 5 seconds!
        CompletableFuture<Result> ret = Futures.timeout((Result)notFound(), 5, TimeUnit.SECONDS).toCompletableFuture();
        mongoService.getModelCollection().find(eq("_id", id)).into(new ArrayList<>(), (o, throwable)-> {
            if (throwable != null)
                ret.complete(notFound());
            else
                ret.complete(ok(Json.toJson(((List) o).get(0))));
        });
        return ret;
    }

    public CompletionStage<Result> create() {
        Document payload = Document.parse(request().body().asJson().textValue());
        String passedId = (String) payload.get("_id");
        final String id = passedId.isEmpty() ? new ObjectId().toString() : passedId;
        payload.put("_id", id);

        CompletableFuture<Result> ret = new CompletableFuture<>();
        mongoService.getModelCollection().insertOne(payload, (aVoid, throwable) -> {
            if (throwable != null)
                ret.complete(internalServerError()); // or badRequest?!
            else
                ret.complete(created().withHeader("Location", id));
        });
        return ret;
    }

    public CompletionStage<Result> update(String id) {
        Document payload = Document.parse(request().body().asJson().textValue());
        CompletableFuture<Result> ret = new CompletableFuture<>();
        mongoService.getModelCollection().updateOne(eq("_id", id), new Document("$set", payload), (updateResult, throwable) -> {
            if (throwable != null)
                ret.complete(internalServerError());
            else
                ret.complete(ok());
        });
        return ret;
    }

    public CompletionStage<Result> delete(String id) {
        CompletableFuture<Result> ret = new CompletableFuture<>();
        mongoService.getModelCollection().deleteOne(eq("_id", id), (deleteResult, throwable) -> {
            if (throwable != null)
                ret.complete(internalServerError());
            else
                ret.complete(ok());
        });
        return ret;
    }
}
