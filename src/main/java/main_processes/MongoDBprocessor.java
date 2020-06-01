package main_processes;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoSocketReadTimeoutException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class MongoDBprocessor {

    //private final String connURL = "mongodb://localhost";
    private final String connURL = "mongodb+srv://admin:admin@timedbcollection-bpbgq.mongodb.net/test?retryWrites=true&w=majority";
    private final String nameDB = "timeDB";
    private final String nameCollection = "timeDbCollection";

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;
    private Document document;

    private ConnectionString connString;
    private MongoClientSettings settings;

    public MongoCollection<Document> connectDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        connString = new ConnectionString(connURL);

        settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        // creating a mongo client 
        mongoClient = MongoClients.create(settings);
        // get a mongo database 
        mongoDatabase = mongoClient.getDatabase(nameDB);
        // get a mongo collection 
        mongoCollection = mongoDatabase.getCollection(nameCollection);
        return mongoCollection;
    }

    public void insertData(ArrayDeque<ZonedDateTime> timeStampArry, MongoCollection<Document> mongoCollection) {
        connIsAvailable(mongoCollection);
        if (!timeStampArry.isEmpty()) {
            try {
                for (int a = 0; a <= timeStampArry.size(); a++) {
                    // insert document to collection mongodb
                    mongoCollection.insertOne(setDoc(timeStampArry));
                }
            } catch (MongoTimeoutException ex) {
                System.out.println(">Connection timed out, trying to reconnect...:");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(MongoDBprocessor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (MongoSocketOpenException | MongoSocketReadTimeoutException ex) {
                System.out.println(">Connection was lost, trying to reconnect...:");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(MongoDBprocessor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    public Document setDoc(ArrayDeque<ZonedDateTime> timeStampArry) {
        // creating a document 
        document = new Document("_id", new ObjectId());
        document.append("time", timeStampArry.pop().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        return document;
    }

    // TODO: 
    public void connIsAvailable(MongoCollection<Document> mongoCollection) {
    }

    // TODO: 
    public void getData() {
    }

    public void close() {
        // closing connection with mongo database
        mongoClient.close();
    }
}
