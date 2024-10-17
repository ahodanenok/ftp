package ahodanenok.ftp.server.transfer;

import java.io.InputStream;
import java.io.OutputStream;

public interface DataTransferHandler {

    void transfer(InputStream in, OutputStream out);
}
