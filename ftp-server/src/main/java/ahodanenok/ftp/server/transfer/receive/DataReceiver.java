package ahodanenok.ftp.server.transfer.receive;

import java.io.InputStream;
import java.io.OutputStream;

public interface DataReceiver {

    void receive(InputStream in, OutputStream out);
}
