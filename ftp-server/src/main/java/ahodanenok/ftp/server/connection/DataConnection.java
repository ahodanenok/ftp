package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.OutputStream;

public interface DataConnection {

    InputStream getInputStream();

    OutputStream getOutputStream();

    boolean isOpened();

    void open();

    void close();
}
