package ahodanenok.ftp.server.transfer.receive;

import java.io.IOException;
import java.io.InputStream;

// todo: better name?
public interface DataReceiveContext {

    boolean isAborted();

    void onBegin() throws IOException;

    void onEnd() throws IOException;

    void onAbort() throws IOException;

    InputStream openConnection() throws IOException;

    void closeConnection() throws IOException;
}
