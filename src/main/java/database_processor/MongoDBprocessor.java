package database_processor;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoSocketReadTimeoutException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Projections.excludeId;

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

    private MongoDB mongoDB;

    private ConnectionString connString;
    private MongoClientSettings settings;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;
    private Document document;

    public MongoDB connectDB() {
        // disable mongodb driver logging (enable just for warnings)
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        // set connection string
        connString = new ConnectionString(connURL);
        // set up behavior for the 'MongoClients'
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
        // pass values to a constructor 
        mongoDB = new MongoDB(connString, settings, mongoClient, mongoDatabase, mongoCollection);

        return mongoDB;
    }

    public void insertData(ArrayDeque<ZonedDateTime> timeStampArry, MongoDB mongoDB) {
        //connIsAvailable(mongoDB);
        if (!timeStampArry.isEmpty()) {
            try {
                for (int a = 0; a <= timeStampArry.size(); a++) {
                    // insert document to collection mongodb
                    mongoCollection = mongoDB.getMongoCollection();
                    mongoCollection.insertOne(setDoc(timeStampArry));
                }
            } catch (MongoTimeoutException ex) {
                System.out.println(">Connection timed out, trying to reconnect...:");
            } catch (MongoSocketOpenException | MongoSocketReadTimeoutException ex) {
                System.out.println(">Connection was lost, trying to reconnect...:");
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
    public void connIsAvailable(MongoDB mongoDB) {
        try {
            FindIterable<Document> fi = mongoCollection.find().projection(excludeId());
            ServerAddress serverAddress = fi.iterator().getServerAddress();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void getData(MongoDB mongoDB) {
        mongoCollection = mongoDB.getMongoCollection();
        FindIterable<Document> fi = mongoCollection.find().projection(excludeId());
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().values());
            }
        } finally {
            System.out.println(">All asdasd");
            cursor.close();
            close(mongoDB);
        }
    }

    public void close(MongoDB mongoDB) {
        // closing connection with mongo database
        mongoDB.getMongoClient().close();
    }

}
