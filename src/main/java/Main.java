

import database.MongoDbProcessor;
import threadService.TimeThread;
import model.MongoDbModel;
import threadService.WriteDbThread;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class Main {

    private static MongoDbProcessor mongoDBprocessor;
    private static MongoDbModel mongoDB;

    private static TimeThread timeThread;
    private static WriteDbThread writeDbThread;

    public static void main(String args[]) {
        mongoDBprocessor = new MongoDbProcessor();
        mongoDB = mongoDBprocessor.connectDB();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p") || args[i].equals("-P")) {
                mongoDBprocessor.getDocuments(mongoDB);
            }
        }
        // create and start the time count thread
        timeThread = new TimeThread(mongoDB);
        // create and start the database processor thread
        writeDbThread = new WriteDbThread(mongoDB);
    }
}
