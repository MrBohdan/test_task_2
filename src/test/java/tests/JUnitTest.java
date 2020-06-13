package tests;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.types.ObjectId;

import model.MongoDbModel;
import database.MongoDbProcessor;

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

    private static MongoCollection<Document> mongoCollection;
    private static Document document;

    private static MongoDbModel mongoDB;
    private static TimeCount timeCount;
    private static MongoDbProcessor mongoDbProcessor;

    private static FindIterable<Document> findIterable;
    private static MongoCursor<Document> cursor;

    private static TimeModel time1;
    private static TimeModel time2;

    @BeforeAll
    @DisplayName("Connect to MongoDB")
    public static void setUp() throws Exception {
        mongoDbProcessor = new MongoDbProcessor();
        mongoDB = mongoDbProcessor.connectDB();
    }

    /**
     * Check if the document was inserted to MongoDB
     *
     */
    @Test
    @DisplayName("Test insert document")
    public void testInsertDocument() throws Exception {
        mongoCollection = mongoDB.getMongoCollection();

        document = new Document("_id", new ObjectId());
        document.append("time", ("00:00:00"));

        mongoCollection.insertOne(document);
        findIterable = mongoCollection.find(document);
        cursor = findIterable.iterator();
        assertEquals(document, cursor.next());

        mongoCollection.deleteOne(document);
    }

    /**
     * Check if a method 'timeCount' working correctly
     *
     */
    @Test
    @DisplayName("Test time count")
    public void testTimeCount() throws Exception {
        time1 = timeCount.timeCount(mongoDB, true);
        time2 = timeCount.timeCount(mongoDB, true);
        assertTrue(time1.getTimestamp().before(time2.getTimestamp()));
    }

    /**
     * Close the MongoDB connection
     *
     */
    @AfterAll
    public static void tearDown() throws Exception {
        mongoDB.getMongoClient().close();
    }
}
