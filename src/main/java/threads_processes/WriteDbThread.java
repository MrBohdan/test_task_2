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
    private TimeCount timeCount;
    private MongoDBprocessor mongoDBprocessor;
    private static MongoDB mongoDB;

    public static final String tname = " Database processor thread : ";

    // database processor thread constructor 
    public WriteDbThread(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        mongoDBprocessor = new MongoDBprocessor();
        mongoDBprocessor.insertDocuments(timeCount.timeStampArry, mongoDB, timeCount);
    }
}
