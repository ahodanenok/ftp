package ahodanenok.ftp.server.connection;

import java.io.IOException;
import java.io.OutputStream;

public interface ControlConnection extends AutoCloseable {

    OutputStream getOutputStream() throws IOException;

    void close() throws IOException;
}
