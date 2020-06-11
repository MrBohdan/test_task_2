package tests;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

import model.MongoDbModel;
import database.MongoDbProcessor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TimeModel;
import service.TimeCount;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class JUnitTest {

    private static ConnectionString connString;
    private static MongoClientSettings settings;
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> mongoCollection;
    private static Document document;
    private static MongoDbProcessor mongoDBprocessor;

    private static MongoDbModel mongoDB;
    private static TimeCount timeCount;
    private static MongoDbProcessor mongoDbProcessor;

    private static FindIterable<Document> findIterable;
    private static MongoCursor<Document> cursor;

    private static TimeModel time1;
    private static TimeModel time2;

    @BeforeAll
    @DisplayName("Connect to MongoDB")
    public static MongoDbModel setUp() throws Exception {
        return mongoDB = mongoDbProcessor.connectDB();
    }

    @Test
    @DisplayName("Test insert document")
    public void testInsertDocument() throws Exception {
        mongoCollection = mongoDB.getMongoCollection();

        document = new Document("_id", new ObjectId());
        document.append("time", ("00:00:00"));

        mongoCollection.insertOne(document);
        findIterable = mongoCollection.find(document);
        cursor = findIterable.iterator();
        // check if the document was inserted to MongoDbModel
        assertEquals(document, cursor.next());
    }

    /**
     * check if a method 'timeCount' working correctly
     */
    @Test
    @DisplayName("Test time count")
    public void testTimeCount() throws Exception {
        time1 = timeCount.timeCount(mongoDB, true);
        time2 = timeCount.timeCount(mongoDB, true);
        assertTrue(time1.getTimestamp().before(time2.getTimestamp()));
    }

    @AfterAll
    public static void tearDown() throws Exception {
        mongoDB.getMongoDatabase().drop(); // drop the test database 
        mongoDB.getMongoClient().close(); // close the MongoDbModel connection
    }
}
