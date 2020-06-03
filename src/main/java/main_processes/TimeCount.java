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

    private static ZonedDateTime zonedDateTime;
    private static ZonedDateTime zonedDateTime2;
    private static WriteDbThread writeDbThread;

    public static boolean threadAlive;

    public static ZonedDateTime timeCount(MongoDB mongoDB, boolean testFlag) {
        while (true) {
            try {
                // get instant time by system time zone offset
                zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS);

                // display time 
                System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

                // add an object of the type 'ZonedDateTime' to an 'ArrayDeque'
                timeStampArry.addLast(zonedDateTime);

                threadAlive = Thread.currentThread().isAlive();
                Thread.sleep(1000);
                if (testFlag == true) {
                    return zonedDateTime;
                }
            } catch (InterruptedException ex) {
                System.out.println(">Thread Interrupted...:");
            }
        }
    }
}
