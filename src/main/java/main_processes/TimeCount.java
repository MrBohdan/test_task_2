package main_processes;

import threads_processes.WriteDbThread;
import com.mongodb.client.MongoCollection;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import org.bson.Document;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeCount {

    public static ArrayDeque<ZonedDateTime> timeStampArry = new ArrayDeque();

    private static ZonedDateTime ldt;
    private static WriteDbThread writeDbThread;

    public static void timeCount(MongoCollection<Document> mongoCollection) {
        while (true) {
            try {
                // get instant time by system time zone offset
                ldt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS);
                
                // display time 
                System.out.println(ldt.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
                
                // add an object of the type 'ZonedDateTime' to an 'ArrayDeque'
                timeStampArry.addLast(ldt);

                // create and start the database processor thread
                writeDbThread = new WriteDbThread(mongoCollection);
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread is interrupted");
            }
        }
    }
}
