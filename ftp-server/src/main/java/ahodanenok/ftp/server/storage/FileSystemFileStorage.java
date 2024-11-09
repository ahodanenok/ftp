package ahodanenok.ftp.server.storage;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import ahodanenok.ftp.server.storage.exception.FileNotFoundException;
import ahodanenok.ftp.server.storage.exception.FilePathInvalidException;
import ahodanenok.ftp.server.storage.exception.FileStorageException;

public final class FileSystemFileStorage implements FileStorage {

    private final Path rootDir;

    public FileSystemFileStorage(String rootDir) {
        this.rootDir = Paths.get(rootDir);
    }

    @Override
    public Stream<String> names(String path) throws FileStorageException {
        Path targetPath = resolvePath(path);
        try {
            return Files.list(targetPath)
                .map(p -> p.getFileName().toString());
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't list names at '%s'", targetPath), e);
        }
    }

    @Override
    public InputStream read(String path) throws FileStorageException {
        Path targetPath = resolvePath(path);
        try {
            return Files.newInputStream(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't read from path '%s'", targetPath), e);
        }
    }

    @Override
    public OutputStream write(String path) throws FileStorageException {
        Path targetPath = resolvePath(path);
        try {
            return Files.newOutputStream(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't write to path '%s'", targetPath), e);
        }
    }

    @Override
    public void delete(String path) throws FileStorageException {
        Path targetPath = resolvePath(path);
        try {
            Files.delete(targetPath);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException(targetPath.toString(), e);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't delete file '%s'", targetPath), e);
        }
    }

    private Path resolvePath(String path) throws FileStorageException {
        Path fullPath;
        try {
            fullPath = rootDir.resolve(path).normalize();
        } catch (InvalidPathException e) {
            throw new FilePathInvalidException(path, e);
        }

        if (!fullPath.startsWith(rootDir)) {
            throw new FilePathInvalidException(path);
        }

        return fullPath;
    }
}
