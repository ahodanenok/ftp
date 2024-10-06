package ahodanenok.ftp.server.request;

import ahodanenok.ftp.server.connection.DataWriter;
import ahodanenok.ftp.server.connection.ResponseWriter;

public interface FtpSession {

    String getCurrentDirectory();

    boolean isDataConnectionOpened();

    void openDataConnection();

    DataWriter getDataWriter();

    ResponseWriter getResponseWriter();
}
