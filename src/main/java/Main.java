
import database.MongoDbProcessor;
import threadService.TimeThread;
import threadService.WriteDbThread;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class Main {

    private static MongoDbProcessor mongoDbProcessor;

    private static TimeThread timeThread;
    private static WriteDbThread writeDbThread;

    public static void main(String args[]) {
        mongoDbProcessor = new MongoDbProcessor();
        mongoDbProcessor.connectDB();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p") || args[i].equals("-P")) {
                mongoDbProcessor.getDocuments();
            }
        }
        // create and start the time count thread
        timeThread = new TimeThread();
        // create and start the database processor thread
        writeDbThread = new WriteDbThread();
    }
}
