package ahodanenok.ftp.server.transfer.send;

import java.io.IOException;
import java.io.OutputStream;

// todo: better name?
public interface DataSendContext {

    boolean isAborted();

    void onBegin() throws IOException;

    void onEnd() throws IOException;

    void onAbort() throws IOException;

    OutputStream openConnection() throws IOException;

    void closeConnection() throws IOException;
}
