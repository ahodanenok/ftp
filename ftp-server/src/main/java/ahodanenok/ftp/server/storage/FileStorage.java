package ahodanenok.ftp.server.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

import ahodanenok.ftp.server.storage.exception.FileStorageException;

public interface FileStorage {

    String getParentPath(String path) throws FileStorageException;

    String resolvePath(String parent, String path) throws FileStorageException;

    Stream<String> names(String path) throws FileStorageException;

    InputStream read(String path) throws FileStorageException;

    OutputStream write(String path) throws FileStorageException;

    String createDirectory(String parent, String path) throws FileStorageException;

    void delete(String path) throws FileStorageException;
}
