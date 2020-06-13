package database;

/**
 *
 * @author Bohdan Skrypnyk
 */
public interface MongoDbConfig {

    // connection strings
    //private final String URL = "mongodb://localhost";
    final String URL = "mongodb+srv://admin:admin@timedbcollection-bpbgq.mongodb.net/test?retryWrites=true&w=majority";
    // how long try to connect before time out
    final String CONNECT_TIMEOUT_MS = "&connectTimeoutMS=2000";
    // how long system will try to send or receive socket before time out
    final String SOCKET_TIMEOUT_MS = "&socketTimeoutMS=2000";
    // how long system will try to write before time out
    final String WRITE_TIMEOUT_MS = "&wtimeoutMS=2000";
    // change default timeout
    final String SERVER_SELECTION_TIMEOUT_MS = "&serverSelectionTimeoutMS=5000";
    final String nameDB = "timeDB";
    final String nameCollection = "timeDbCollection";
    
    StringBuffer stringBuffer = new StringBuffer(URL);
    final String connectionString = stringBuffer.append(CONNECT_TIMEOUT_MS)
            .append(SOCKET_TIMEOUT_MS)
            .append(WRITE_TIMEOUT_MS)
            .append(SERVER_SELECTION_TIMEOUT_MS).toString();
}
