package ahodanenok.ftp.server.request;

import java.io.InputStream;
import java.io.OutputStream;

import ahodanenok.ftp.server.connection.ResponseWriter;

public interface FtpSession {

    String getCurrentDirectory();

    boolean isDataConnectionOpened();

    void openDataConnection();

    InputStream getDataInputStream();

    OutputStream getDataOutputStream();

    ResponseWriter getResponseWriter();
}
