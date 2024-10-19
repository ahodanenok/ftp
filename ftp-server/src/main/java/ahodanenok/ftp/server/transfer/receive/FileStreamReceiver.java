package ahodanenok.ftp.server.transfer.receive;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public final class FileStreamReceiver implements DataReceiver {

    @Override
    public void receive(InputStream in, OutputStream out) {
        try {
            int length;
            byte[] buf = new byte[8092];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
