package ahodanenok.ftp.server.utils;

import java.io.IOException;
import java.io.OutputStream;

public final class IOUtils {

    public static void writeAscii(String name, OutputStream out) throws IOException {
        out.write(name.getBytes("US-ASCII"));
    }
}
