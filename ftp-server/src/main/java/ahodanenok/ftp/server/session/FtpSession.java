package ahodanenok.ftp.server.session;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.response.ResponseWriter;

public interface FtpSession {

    String getCurrentDirectory();

    DataConnection getDataConnection();

    ResponseWriter getResponseWriter();
}
