package ahodanenok.ftp.server.transfer.receive;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.function.BooleanSupplier;

public final class FileStreamReceiver implements DataReceiver {

    private BooleanSupplier isAborted;

    public void setIsAborted(BooleanSupplier isAborted) {
        this.isAborted = isAborted;
    }

    @Override
    public boolean receive(InputStream in, OutputStream out) throws IOException {
        int length;
        byte[] buf = new byte[8092];
        while ((length = in.read(buf)) != -1) {
            if (isAborted.getAsBoolean()) {
                return false;
            }

            out.write(buf, 0, length);
        }
        out.close();
        return true;
    }
}
