package threads_processes;

import main_processes.TimeCount;
import database_processor.MongoDB;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeThread implements Runnable {

    private Thread tred;
    private TimeCount tc;
    private static MongoDB mongoDB;
    //private MongoDBprocessor mongoDBprocessor = new MongoDBprocessor();

    private static final String tname = " Time count thread : ";

    public TimeThread(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        tc.timeCount(mongoDB);
    }
}
