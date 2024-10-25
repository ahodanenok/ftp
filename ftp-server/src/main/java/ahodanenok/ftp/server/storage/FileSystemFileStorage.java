package ahodanenok.ftp.server.storage;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class FileSystemFileStorage implements FileStorage {

    private final Path rootDir;

    public FileSystemFileStorage(String rootDir) {
        this.rootDir = Paths.get(rootDir);
    }

    @Override
    public Stream<String> names(String path) throws FileStorageException {
        Path targetPath = rootDir.resolve(path);
        try {
            return Files.list(targetPath)
                .map(p -> p.getFileName().toString());
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't list names at '%s'", targetPath), e);
        }
    }

    @Override
    public InputStream read(String path) throws FileStorageException {
        Path targetPath = rootDir.resolve(path);
        try {
            return Files.newInputStream(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't read from path '%s'", targetPath), e);
        }
    }

    @Override
    public OutputStream write(String path) throws FileStorageException {
        Path targetPath = rootDir.resolve(path);
        try {
            return Files.newOutputStream(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't write to path '%s'", targetPath), e);
        }
    }
}
