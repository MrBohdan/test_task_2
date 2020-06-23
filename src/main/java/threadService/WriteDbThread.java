package threadService;

import database.MongoDbProcessor;
import service.TimeCount;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class WriteDbThread implements Runnable {

    private Thread tred;
    private TimeCount timeCount;
    private MongoDbProcessor mongoDBprocessor;

    public static final String tname = " Database processor thread : ";

    // database processor thread constructor 
    public WriteDbThread() {
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        mongoDBprocessor = new MongoDbProcessor();
        mongoDBprocessor.insertDocuments(timeCount.timeStampArry, timeCount);
    }
}
