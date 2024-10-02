package ahodanenok.ftp.server.connection;

import java.io.IOException;

public interface DataWriter {

    void write(byte[] data) throws IOException;
}
