package ahodanenok.ftp.server.storage;

import java.util.stream.Stream;

public interface FileStorage {

    Stream<String> names(String path);
}
