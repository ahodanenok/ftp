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
    public Stream<String> names(String path) {
        try {
            return Files.list(rootDir.resolve(path))
                .map(p -> p.getFileName().toString());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public InputStream read(String path) {
        try {
            return Files.newInputStream(rootDir.resolve(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public OutputStream write(String path) {
        try {
            return Files.newOutputStream(rootDir.resolve(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
