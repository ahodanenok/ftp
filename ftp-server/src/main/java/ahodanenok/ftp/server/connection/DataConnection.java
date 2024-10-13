package ahodanenok.ftp.server.connection;

import java.io.OutputStream;

public interface DataConnection {

    OutputStream getOutputStream();

    boolean isOpened();

    void open();

    void close();
}
