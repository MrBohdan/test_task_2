package threadService;

import database.MongoDbProcessor;
import service.TimeCount;
import model.MongoDbModel;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class WriteDbThread implements Runnable {

    private Thread tred;
    private TimeCount timeCount;
    private MongoDbProcessor mongoDBprocessor;
    private static MongoDbModel mongoDB;

    public static final String tname = " Database processor thread : ";

    // database processor thread constructor 
    public WriteDbThread() {
        this.mongoDB = mongoDB;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        mongoDBprocessor = new MongoDbProcessor();
        mongoDBprocessor.insertDocuments(timeCount.timeStampArry, timeCount);
    }
}
