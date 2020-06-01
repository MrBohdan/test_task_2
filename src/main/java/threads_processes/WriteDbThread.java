package threads_processes;

import database_processor.MongoDBprocessor;
import main_processes.TimeCount;
import database_processor.MongoDB;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class WriteDbThread implements Runnable {

    private Thread tred;
    private TimeCount tc;
    private MongoDBprocessor tdb;
    private static MongoDB mongoDB;

    public static final String tname = " Database processor thread : ";

    public WriteDbThread(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        tdb = new MongoDBprocessor();
        tdb.insertData(tc.timeStampArry, mongoDB);
    }
}
