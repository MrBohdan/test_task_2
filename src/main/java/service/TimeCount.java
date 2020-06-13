package service;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import model.TimeModel;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeCount {

    public static TimeModel time;
    private static Timestamp timestamp;
    
    public static ArrayDeque<TimeModel> timeStampArry = new ArrayDeque();

    /**
     * Check if a method 'timeCount' working correctly
     *
     * @param testFlag used to stop while-loop for JUnit testing.
     * @return the current timestamp.
     */
    public static TimeModel timeCount(boolean testFlag) {
        while (true) {
            try {
                timestamp = new Timestamp(System.currentTimeMillis());
                time = new TimeModel(timestamp);
                
                time.setTimestamp(timestamp);
                
                System.out.println(time.show());
                // add an object of the type 'TimeModel' to an 'ArrayDeque'
                timeStampArry.addLast(new TimeModel(timestamp));

                Thread.sleep(1000);
                //TimeUnit.SECONDS.sleep(1);
                if (testFlag == true) {
                    return time;
                }
            } catch (InterruptedException ex) {
                System.out.println(">Thread Interrupted...:");
            }
        }
    }

}
