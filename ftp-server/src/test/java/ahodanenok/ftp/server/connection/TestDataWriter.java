package ahodanenok.ftp.server.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class TestDataWriter implements DataWriter {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public void write(byte[] data) {
        try {
            out.write(data);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void write(byte[] data, int offset, int length) {
        out.write(data, offset, length);
    }

    public void write(String data) {
        try {
            out.write(data.getBytes("US-ASCII"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void newLine() {
        out.write('\r');
        out.write('\n');
    }

    public byte[] toBytes() {
        return out.toByteArray();
    }

    public String toAsciiString() {
        try {
            return out.toString("US-ASCII");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
