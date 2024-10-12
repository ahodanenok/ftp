package ahodanenok.ftp.server.request;

import java.io.OutputStream;

import ahodanenok.ftp.server.connection.DataWriter;
import ahodanenok.ftp.server.connection.ResponseWriter;

public interface FtpSession {

    String getCurrentDirectory();

    boolean isDataConnectionOpened();

    void openDataConnection();

    DataWriter getDataWriter();

    default OutputStream getDataOutputStream() {
        return null;
    }

    ResponseWriter getResponseWriter();
}
