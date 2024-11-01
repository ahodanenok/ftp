package ahodanenok.ftp.server.transfer.receive;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class FileStreamReceiver implements DataReceiver {

    @Override
    public void receive(OutputStream out, DataReceiveContext context) throws IOException {
        context.onBegin();
        InputStream in = context.openConnection();

        int length;
        byte[] buf = new byte[8092];
        while ((length = in.read(buf)) != -1) {
            if (context.isAborted()) {
                context.onAbort();
                return;
            }

            out.write(buf, 0, length);
        }

        context.closeConnection();
        context.onEnd();
    }
}
