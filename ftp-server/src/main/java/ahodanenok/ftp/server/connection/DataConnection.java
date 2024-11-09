package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;

public interface DataConnection extends AutoCloseable {

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    void setRemoteHostPort(InetAddress host, int port) throws IOException;

    void setPassiveMode() throws IOException;

    InetAddress getLocalHost();

    int getLocalPort();

    boolean isOpened();

    void open() throws IOException;

    void close() throws IOException;
}
