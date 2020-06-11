package database;

import model.MongoDB;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoConfigurationException;
import com.mongodb.MongoSecurityException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoSocketReadTimeoutException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.bson.types.ObjectId;

import service.TimeCount;
import static com.mongodb.client.model.Projections.excludeId;
import java.sql.Timestamp;
import model.Time;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class MongoDBprocessor {

    // connection strings
    //private final String connURL = "mongodb://localhost";
    private final String connURL = "mongodb+srv://admin:admin@timedbcollection-bpbgq.mongodb.net/test?retryWrites=true&w=majority";
    // how long try to connect before time out
    private final String CONNECT_TIMEOUT_MS = "&connectTimeoutMS=2000";
    // how long system will try to send or receive socket before time out
    private final String SOCKET_TIMEOUT_MS = "&socketTimeoutMS=2000";
    // how long system will try to write before time out
    private final String WRITE_TIMEOUT_MS = "&wtimeoutMS=2000";
    // change default timeout
    private final String SERVER_SELECTION_TIMEOUT_MS = "&serverSelectionTimeoutMS=5000";
    private final String nameDB = "timeDB";
    private final String nameCollection = "timeDbCollection";

    private MongoDB mongoDB;

    private ConnectionString connString;
    private MongoClientSettings settings;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;
    private Document document;

    private FindIterable<Document> findIterable;
    private MongoCursor<Document> cursor;

    public MongoDB connectDB() {
        try {
            // set connection string
            connString = new ConnectionString(connURL + CONNECT_TIMEOUT_MS + SOCKET_TIMEOUT_MS + WRITE_TIMEOUT_MS + SERVER_SELECTION_TIMEOUT_MS);
            // set up behavior for the 'MongoClients'
            settings = MongoClientSettings.builder()
                    .applyToConnectionPoolSettings((b) -> b.maxWaitTime(2000, TimeUnit.SECONDS)) // block waiting for connection 
                    .applyConnectionString(connString)
                    .retryWrites(true)
                    .build();

            // creating a mongo client 
            mongoClient = MongoClients.create(settings);
            // get a mongo database 
            mongoDatabase = mongoClient.getDatabase(nameDB);
            // get a mongo collection 
            mongoCollection = mongoDatabase.getCollection(nameCollection);
        } catch (MongoConfigurationException | MongoSocketOpenException ex) {
            System.out.println(">Connection lost, trying to reconnect...:");
            connectDB();
        }
        // pass values to a constructor 
        mongoDB = new MongoDB(connString, settings, mongoClient, mongoDatabase, mongoCollection);

        return mongoDB;
    }
    // to insert data to the database

    public void insertDocuments(ArrayDeque<Time> timeStampArry, MongoDB mongoDB, TimeCount timeCount) {
        try {
            Thread.sleep(1000);
            while (true) {
                Thread.sleep(1000);
                if (!timeStampArry.isEmpty()) {
                    try {
                        for (int a = 0; a <= timeStampArry.size(); a++) {
                            // get collection 
                            mongoCollection = mongoDB.getMongoCollection();
                            // insert document to collection mongodb
                            mongoCollection.insertOne(setDocument(timeStampArry));
                        }
                    } catch (MongoTimeoutException ex) {
                        System.out.println(">Connection timed out, trying to reconnect...:");
                    } catch (MongoSocketOpenException | MongoSecurityException | MongoSocketReadTimeoutException ex) {
                        System.out.println(">Connection was lost, trying to reconnect...:");
                    }
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(">Thread Interrupted...:");
        }
    }

    public Document setDocument(ArrayDeque<Time> timeStampArry) {
        // creating a document 
        document = new Document("_id", new ObjectId());
        document.append("time", new Timestamp(timeStampArry.pop().getTimestamp().getTime()));
        return document;
    }

    //  get values from the database and display them
    public void getDocuments(MongoDB mongoDB) {
        // get collection 
        mongoCollection = mongoDB.getMongoCollection();

        // get documents from the collection
        findIterable = mongoCollection.find().projection(excludeId());

        // initialize iterator 
        cursor = findIterable.iterator();
        try {
            while (cursor.hasNext()) {
                // display values
                System.out.println(cursor.next().values());
            }
        } finally {
            cursor.close();
            close(mongoDB);
            SystemExit();
        }
    }

    public void close(MongoDB mongoDB) {
        // closing connection with mongo database
        mongoDB.getMongoClient().close();
    }

    // to exit from the system 
    public void SystemExit() {
        System.exit(0);
    }
}
