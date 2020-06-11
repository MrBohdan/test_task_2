package model;

import java.sql.Timestamp;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class Time {

    private Timestamp timestamp;

    public Time(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Time() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String show() {
        return "Time{" + "timestamp=" + timestamp + '}';
    }

}
