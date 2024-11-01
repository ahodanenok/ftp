package ahodanenok.ftp.server.transfer.receive;

import java.io.IOException;
import java.io.OutputStream;

public interface DataReceiver {

    void receive(OutputStream out, DataReceiveContext context) throws IOException;
}
