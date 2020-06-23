package database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoConfigurationException;
import com.mongodb.MongoSecurityException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoSocketReadTimeoutException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Projections.excludeId;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

import org.bson.Document;
import org.bson.types.ObjectId;

import service.TimeCount;
import model.TimeModel;
import model.MongoDbModel;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class MongoDbProcessor implements MongoDbConfig {

    private static MongoDbModel mongoDbModel;
    private Document document;

    private FindIterable<Document> findIterable;
    private MongoCursor<Document> cursor;

    /**
     * Establish connecting with the MongoDB.
     *
     */
    public MongoDbModel connectDB() {
        mongoDbModel = new MongoDbModel();
        ServerConnection serverConnection = new ServerConnection();
        try {
            // set connection string
            mongoDbModel.setConnString(new ConnectionString(CONNECTION_STRING));
            // set up behavior for the 'MongoClients'
            mongoDbModel.setSettings(MongoClientSettings.builder()
                    .applyToConnectionPoolSettings((b) -> b.maxWaitTime(2000, TimeUnit.SECONDS)) // block waiting for connection 
                    .applyToServerSettings((b) -> b.addServerMonitorListener(serverConnection))
                    .applyConnectionString(mongoDbModel.getConnString())
                    .retryWrites(true)
                    .build());

            // creating a mongo client 
            mongoDbModel.setMongoClient(MongoClients.create(mongoDbModel.getSettings()));
            // get a mongo database 
            mongoDbModel.setMongoDatabase(mongoDbModel.getMongoClient().getDatabase(NAME_DB));
            // get a mongo collection 
            mongoDbModel.setMongoCollection(mongoDbModel.getMongoDatabase().getCollection(NAME_COLLECTION));
        } catch (MongoConfigurationException | MongoSocketOpenException ex) {
            System.out.println("{Connection lost, trying to reconnect...:}");
        }
        return mongoDbModel;
    }

    /**
     * To insert data to the database.
     *
     */
    public void insertDocuments(ArrayDeque<TimeModel> timeStampArry, TimeCount timeCount) {
        try {
            Thread.sleep(1000);
            while (true) {
                Thread.sleep(1000);
                if (!timeStampArry.isEmpty()) {
                    try {
                        for (int a = 0; a <= timeStampArry.size(); a++) {
                            // get collection and insert document to collection of the mongodb
                            mongoDbModel.getMongoCollection().insertOne(setDocument(timeStampArry));
                        }
                    } catch (MongoTimeoutException ex) {
                        System.out.println("Time{" + timeCount.time.getTimestamp() + "} Connection timed out, trying to reconnect...:");
                    } catch (MongoSocketOpenException | MongoSecurityException | MongoSocketReadTimeoutException ex) {
                        System.out.println("Time{" + timeCount.time.getTimestamp() + "} Connection was lost, trying to reconnect...:");
                    }
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Time{" + timeCount.time.getTimestamp() + "} Thread Interrupted...:");
        }
    }

    /**
     * Creating a document.
     *
     */
    public Document setDocument(ArrayDeque<TimeModel> timeStampArry) {
        document = new Document("_id", new ObjectId());
        document.append("time", new Timestamp(timeStampArry.pop().getTimestamp().getTime()));
        return document;
    }

    //  get values from the database and display them
    public void getDocuments() {
        // get collection 
        // get documents from the collection
        findIterable = mongoDbModel.getMongoCollection().find().projection(excludeId());

        // initialize iterator 
        cursor = findIterable.iterator();
        try {
            while (cursor.hasNext()) {
                // display values
                System.out.println(cursor.next().values());
            }
        } finally {
            cursor.close();
            close(mongoDbModel);
            SystemExit();
        }
    }

    /**
     * Closing connection with the mongo database.
     *
     */
    public void close(MongoDbModel mongoDB) {
        mongoDB.getMongoClient().close();
    }

    /**
     * Terminate the system running.
     *
     */
    public void SystemExit() {
        System.exit(0);
    }
}
