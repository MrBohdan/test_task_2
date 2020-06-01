package main_processes;

import threads_processes.TimeThread;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class Main {

    private static MongoDBprocessor tdb;
    private static TimeThread timeThread;
    private static MongoCollection<Document> mongoCollection;

    public static void main(String args[]) {

        tdb = new MongoDBprocessor();
        mongoCollection = tdb.connectDB();

        // create and start the time count thread
        timeThread = new TimeThread(mongoCollection);

    }
}
