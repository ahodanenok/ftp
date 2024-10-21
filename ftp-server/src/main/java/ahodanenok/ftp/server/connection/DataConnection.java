package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public interface DataConnection {

    InputStream getInputStream();

    OutputStream getOutputStream();

    void setHostPort(InetAddress host, int port);

    boolean isOpened();

    void open();

    void close();
}
