
import database.MongoDbProcessor;
import threadService.TimeThread;
import model.MongoDbModel;
import threadService.WriteDbThread;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class Main {

    private static MongoDbProcessor mongoDbProcessor;
    private static MongoDbModel mongoDB;

    private static TimeThread timeThread;
    private static WriteDbThread writeDbThread;

    public static void main(String args[]) {
        mongoDbProcessor = new MongoDbProcessor();
        try {
            mongoDB = mongoDbProcessor.connectDB();
        } finally {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-p") || args[i].equals("-P")) {
                    mongoDbProcessor.getDocuments(mongoDB);
                }
            }
            // create and start the time count thread
            timeThread = new TimeThread(mongoDB);
            // create and start the database processor thread
            writeDbThread = new WriteDbThread(mongoDB);
        }
    }
}
