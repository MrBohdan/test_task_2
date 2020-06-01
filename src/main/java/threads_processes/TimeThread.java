package threads_processes;

import main_processes.TimeCount;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeThread implements Runnable {

    private Thread tred;
    private TimeCount tc;
    private final MongoCollection<Document> mongoCollection;

    private static final String tname = " Time count thread : ";

    public TimeThread(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        tc.timeCount(mongoCollection);
    }
}
