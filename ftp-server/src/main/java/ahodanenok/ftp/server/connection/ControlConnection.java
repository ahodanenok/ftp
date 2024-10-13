package ahodanenok.ftp.server.connection;

import java.io.OutputStream;

public interface ControlConnection {

    OutputStream getOutputStream();

    void close();
}
