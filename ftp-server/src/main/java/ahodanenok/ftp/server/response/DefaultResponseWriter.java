package ahodanenok.ftp.server.response;

import java.io.IOException;
import java.io.OutputStream;

public final class DefaultResponseWriter implements ResponseWriter {

    private final OutputStream out;

    public DefaultResponseWriter(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int code, String description) throws IOException {
        out.write((code + "").getBytes("US-ASCII"));
        out.write(' ');
        out.write(description.getBytes("US-ASCII"));
        out.write('\r');
        out.write('\n');
    }
}
