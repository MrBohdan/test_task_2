package model;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class MongoDbModel {

    private final ConnectionString connString;
    private final MongoClientSettings settings;
    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> mongoCollection;

    // constructor for the 'MongoDbModel'
    public MongoDbModel(ConnectionString connString, MongoClientSettings settings, MongoClient mongoClient, MongoDatabase mongoDatabase, MongoCollection<Document> mongoCollection) {
        this.connString = connString;
        this.settings = settings;
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
        this.mongoCollection = mongoCollection;
    }

    // getters
    public ConnectionString getConnString() {
        return connString;
    }

    public MongoClientSettings getSettings() {
        return settings;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }

}
