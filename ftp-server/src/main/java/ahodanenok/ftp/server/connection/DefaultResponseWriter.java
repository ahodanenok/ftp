package ahodanenok.ftp.server.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import ahodanenok.ftp.server.request.FtpReply;

public final class DefaultResponseWriter implements ResponseWriter {

    private final OutputStream out;

    public DefaultResponseWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(FtpReply reply) {
        try {
            out.write((reply.getCode() + "").getBytes("US-ASCII"));
            out.write(' ');
            out.write(reply.getDescription().getBytes("US-ASCII"));
            out.write('\r');
            out.write('\n');
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}