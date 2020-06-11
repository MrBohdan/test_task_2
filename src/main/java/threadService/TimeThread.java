package threadService;

import service.TimeCount;
import model.MongoDbModel;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeThread implements Runnable {

    private Thread tred;
    private TimeCount timeCount;
    private static MongoDbModel mongoDB;
    
    private static final String tname = " Time count thread : ";
    
    // time count thread constructor 
    public TimeThread(MongoDbModel mongoDB) {
        this.mongoDB = mongoDB;
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        timeCount.timeCount(mongoDB, false);
    }
}
