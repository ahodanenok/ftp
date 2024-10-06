package ahodanenok.ftp.server.storage;

import java.io.InputStream;
import java.util.stream.Stream;

public interface FileStorage {

    Stream<String> names(String path);

    InputStream read(String path);
}
