package ahodanenok.ftp.server.session;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import ahodanenok.ftp.server.response.ResponseWriter;

public interface FtpSession {

    String getCurrentDirectory();

    void changeDataHostPort(InetAddress host, int port);

    boolean isDataConnectionOpened();

    void openDataConnection();

    InputStream getDataInputStream();

    OutputStream getDataOutputStream();

    ResponseWriter getResponseWriter();
}
