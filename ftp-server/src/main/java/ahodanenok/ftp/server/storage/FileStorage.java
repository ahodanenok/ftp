package ahodanenok.ftp.server.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public interface FileStorage {

    Stream<String> names(String path) throws FileStorageException;

    InputStream read(String path) throws FileStorageException;

    OutputStream write(String path) throws FileStorageException;
}
