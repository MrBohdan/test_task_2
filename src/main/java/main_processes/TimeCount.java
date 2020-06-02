package main_processes;

import threads_processes.WriteDbThread;
import database_processor.MongoDB;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeCount {

    public static ArrayDeque<ZonedDateTime> timeStampArry = new ArrayDeque();
    public static ArrayDeque<ZonedDateTime> asd = new ArrayDeque();

    private static ZonedDateTime ldt;
    private static WriteDbThread writeDbThread;
    public static boolean threadAlive;

    public static void timeCount(MongoDB mongoDB) {
        while (true) {
            try {
                // get instant time by system time zone offset
                ldt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS);

                // display time 
                System.out.println(ldt.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

                // add an object of the type 'ZonedDateTime' to an 'ArrayDeque'
                timeStampArry.addLast(ldt);

                threadAlive = Thread.currentThread().isAlive();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread is interrupted");
            }
        }
    }
}
