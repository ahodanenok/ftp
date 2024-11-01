package ahodanenok.ftp.server.transfer.send;

import java.io.InputStream;
import java.io.IOException;

public interface DataSender {

    void send(InputStream in, DataSendContext context) throws IOException;
}
