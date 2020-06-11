package service;

import java.sql.Timestamp;
import java.util.ArrayDeque;

import model.MongoDbModel;
import model.TimeModel;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeCount {

    private static TimeModel time;
    private static Timestamp timestamp;

    public static ArrayDeque<TimeModel> timeStampArry = new ArrayDeque();

    public static TimeModel timeCount(MongoDbModel mongoDB, boolean testFlag) {
        while (true) {
            try {
                timestamp = new Timestamp(System.currentTimeMillis());
                time = new TimeModel(timestamp);

                System.out.println(time.show());
                // add an object of the type 'ZonedDateTime' to an 'ArrayDeque'
                timeStampArry.addLast(new TimeModel(timestamp));

                Thread.sleep(1000);
                if (testFlag == true) {
                    return time;
                }
            } catch (InterruptedException ex) {
                System.out.println(">Thread Interrupted...:");
            }
        }
    }
    
}
