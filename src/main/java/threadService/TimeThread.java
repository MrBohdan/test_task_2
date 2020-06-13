package threadService;

import service.TimeCount;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeThread implements Runnable {

    private Thread tred;
    private TimeCount timeCount;

    private static final String tname = " Time count thread : ";

    // time count thread constructor 
    public TimeThread() {
        this.tred = new Thread(this, tname);
        this.tred.start();
    }

    @Override
    public void run() {
        timeCount.timeCount(false);
    }
}
