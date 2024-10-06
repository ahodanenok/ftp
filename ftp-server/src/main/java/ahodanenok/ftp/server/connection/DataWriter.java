package ahodanenok.ftp.server.connection;

import java.io.IOException;

public interface DataWriter {

    void write(byte[] data); // throws IOException;

    void write(String data); // throws IOException;

    void newLine(); // throws IOException;
}
