package model;

import java.sql.Timestamp;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class TimeModel {

    private Timestamp timestamp;

    public TimeModel(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public TimeModel() {
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
