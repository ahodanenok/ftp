package ahodanenok.ftp.server.transfer.send;

import java.io.InputStream;
import java.io.OutputStream;

public interface DataSender {

    void send(InputStream in, OutputStream out);
}
