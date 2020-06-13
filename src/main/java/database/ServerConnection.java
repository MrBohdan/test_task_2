package database;

import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;

import service.TimeCount;

/**
 *
 * @author Bohdan Skrypnyk
 */
public class ServerConnection implements ServerMonitorListener {

    private static TimeCount timeCount;

    @Override
    public void serverHearbeatStarted(ServerHeartbeatStartedEvent serverHeartbeatStartedEvent) {
    }

    @Override
    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent serverHeartbeatSucceededEvent) {
        //   System.out.println(timeCount.time.getTimestamp() + " ServerHeartbeatSucceededEvent >>>>>> " + serverHeartbeatSucceededEvent.getReply().toJson());
        //  System.out.println(timeCount.time.getTimestamp() + " ServerHeartbeatSucceededEvent >>>>>> " + serverHeartbeatSucceededEvent.getConnectionId().getServerId().getAddress());
    }

    @Override
    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent serverHeartbeatFailedEvent) {
        //  System.out.println(timeCount.time.getTimestamp() + " FServerHeartbeatFailedEvent >>>>>> " + serverHeartbeatFailedEvent.getThrowable().getMessage());
    }

}
