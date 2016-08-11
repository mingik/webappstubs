package services;

import com.mongodb.WriteConcern;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mintik on 8/10/16.
 */
public class MongoService {
    private MongoClient client = MongoClients.create(); // uses localhost:27027 by default

    private Configuration configuration;
    private String dbName;
    private String collectionName;


    @Inject
    public MongoService(Configuration configuration) {
        this.configuration = configuration;
        initFields();
    }

    private void initFields() {
        dbName = configuration.getString("mongodb.dbname");
        collectionName = configuration.getString("mongodb.colname");
    }


    public MongoCollection getModelCollection() {
        return client.getDatabase(dbName).getCollection(collectionName).withWriteConcern(WriteConcern.JOURNALED); }
}
