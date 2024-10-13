package ahodanenok.ftp.server.request;

import java.io.OutputStream;

import ahodanenok.ftp.server.connection.ResponseWriter;

public interface FtpSession {

    String getCurrentDirectory();

    boolean isDataConnectionOpened();

    void openDataConnection();

    OutputStream getDataOutputStream();

    ResponseWriter getResponseWriter();
}
