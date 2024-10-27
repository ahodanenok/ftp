package ahodanenok.ftp.server.transfer.receive;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.BooleanSupplier;

public interface DataReceiver {

    void setIsAborted(BooleanSupplier isAborted);

    boolean receive(InputStream in, OutputStream out) throws IOException;
}
