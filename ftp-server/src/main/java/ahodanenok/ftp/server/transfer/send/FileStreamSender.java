package ahodanenok.ftp.server.transfer.send;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class FileStreamSender implements DataSender {

    @Override
    public void send(InputStream in, DataSendContext context) throws IOException {
        context.onBegin();
        OutputStream out = context.openConnection();

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
