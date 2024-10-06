package ahodanenok.ftp.server.storage;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
