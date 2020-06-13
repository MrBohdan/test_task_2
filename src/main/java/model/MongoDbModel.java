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

    private static ConnectionString connString;
    private static MongoClientSettings settings;
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> mongoCollection;

    // constructor for the 'MongoDbModel'
    public MongoDbModel(ConnectionString connString, MongoClientSettings settings, MongoClient mongoClient, MongoDatabase mongoDatabase, MongoCollection<Document> mongoCollection) {
        this.connString = connString;
        this.settings = settings;
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
        this.mongoCollection = mongoCollection;
    }

    public MongoDbModel() {

    }

    // setters
    public void setConnString(ConnectionString connString) {
        this.connString = new ConnectionString(connString.toString());
    }

    public void setSettings(MongoClientSettings settings) {
        this.settings = settings;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public void setMongoCollection(MongoCollection<Document> mongoCollection) {
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
