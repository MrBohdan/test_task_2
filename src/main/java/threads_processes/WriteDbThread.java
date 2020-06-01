package threads_processes;

import main_processes.MongoDBprocessor;
import main_processes.TimeCount;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class WriteDbThread implements Runnable {

    private Thread tred;
    private TimeCount tc;
    private MongoDBprocessor tdb;
    private final MongoCollection<Document> mongoCollection;

    public static final String tname = " Database processor thread : ";

    public WriteDbThread(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        tdb = new MongoDBprocessor();
        tdb.insertData(tc.timeStampArry, mongoCollection);
    }
}
