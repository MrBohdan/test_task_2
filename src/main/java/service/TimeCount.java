package service;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import threadService.WriteDbThread;

import java.sql.Timestamp;
import java.util.ArrayDeque;

import model.MongoDB;
import model.Time;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeCount {

    private static Time time;
    private static Timestamp timestamp;

    public static ArrayDeque<Time> timeStampArry = new ArrayDeque();

    private static final Logger log = LoggerFactory.getLogger(TimeCount.class);

    public static Time timeCount(MongoDB mongoDB, boolean testFlag) {
        while (true) {
            try {
                timestamp = new Timestamp(System.currentTimeMillis());
                time = new Time(timestamp);

                System.out.println(time.show());
                // add an object of the type 'ZonedDateTime' to an 'ArrayDeque'
                timeStampArry.addLast(new Time(timestamp));


                log.debug("Save new timestamp: {}", timestamp);

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
