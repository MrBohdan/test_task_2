package main_processes;

import database_processor.MongoDBprocessor;
import threads_processes.TimeThread;
import database_processor.MongoDB;
import threads_processes.WriteDbThread;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class Main {

    private static MongoDBprocessor tdb;
    private static MongoDB mongoDB;

    private static TimeThread timeThread;
    private static WriteDbThread writeDbThread;

    public static void main(String args[]) {
        tdb = new MongoDBprocessor();
        mongoDB = tdb.connectDB();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p") || args[i].equals("-P")) {
                tdb.getData(mongoDB);
            }
        }
        // create and start the time count thread
        timeThread = new TimeThread(mongoDB);
        // create and start the database processor thread
        writeDbThread = new WriteDbThread(mongoDB);
    }
}
