package ahodanenok.ftp.server.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public final class DefaultResponseWriter implements ResponseWriter {

    private final OutputStream out;

    public DefaultResponseWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(FtpReply reply) throws IOException {
        out.write((reply.getCode() + "").getBytes("US-ASCII"));
        out.write(' ');
        out.write(reply.getDescription().getBytes("US-ASCII"));
        out.write('\r');
        out.write('\n');
    }
}