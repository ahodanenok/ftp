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

    private final Path storageRoot;
    private final String pathSeparator;

    public FileSystemFileStorage(String storageRoot) {
        this.storageRoot = Paths.get(storageRoot);
        this.pathSeparator = this.storageRoot.getFileSystem().getSeparator();
    }

    @Override
    public String getParentPath(String path) throws FileStorageException {
        Path targetPath = resolvePathInternal(storageRoot, path);
        if (targetPath.equals(storageRoot)) {
            return pathSeparator;
        }

        Path parentPath = targetPath.getParent();
        if (parentPath == null) {
            return pathSeparator;
        }

        return pathToString(parentPath);
    }

    @Override
    public String resolvePath(String parent, String path) throws FileStorageException {
        Path parentPath = resolvePathInternal(storageRoot, parent);
        Path targetPath = resolvePathInternal(parentPath, path);
        return pathToString(targetPath);
    }

    @Override
    public Stream<String> names(String path) throws FileStorageException {
        Path targetPath = resolvePathInternal(storageRoot, path);
        try {
            return Files.list(targetPath)
                .map(p -> p.getFileName().toString());
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't list names at '%s'", targetPath), e);
        }
    }

    @Override
    public InputStream read(String path) throws FileStorageException {
        Path targetPath = resolvePathInternal(storageRoot, path);
        try {
            return Files.newInputStream(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't read from path '%s'", targetPath), e);
        }
    }

    @Override
    public OutputStream write(String path) throws FileStorageException {
        Path targetPath = resolvePathInternal(storageRoot, path);
        try {
            Files.createDirectories(targetPath.getParent());
        } catch (IOException e) {
            throw new FileStorageException(String.format(
                "Can't create directories for path '%s'", targetPath));
        }
        try {
            return Files.newOutputStream(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(
                String.format("Can't write to path '%s'", targetPath), e);
        }
    }

    @Override
    public String createDirectory(String parent, String path) throws FileStorageException {
        Path parentPath = resolvePathInternal(storageRoot, parent);
        Path targetPath = resolvePathInternal(parentPath, path);
        if (Files.exists(targetPath)) {
            throw new FileStorageException(String.format("Path '%s' already exists", path));
        }

        try {
            Files.createDirectories(targetPath);
        } catch (IOException e) {
            throw new FileStorageException(
                String.format("Can't create a directory at path '%s'", path), e);
        }

        return pathToString(targetPath);
    }

    @Override
    public void delete(String path) throws FileStorageException {
        Path targetPath = resolvePathInternal(storageRoot, path);
        try {
            Files.delete(targetPath);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException(targetPath.toString(), e);
        } catch (IOException e) {
            throw new FileStorageException(String.format("Can't delete file '%s'", targetPath), e);
        }
    }

    private String pathToString(Path path) {
        return pathSeparator + storageRoot.relativize(path).toString();
    }

    private Path resolvePathInternal(Path root, String pathStr) throws FileStorageException {
        Path fullPath;
        try {
            Path path = Path.of(pathStr);
            if (path.getRoot() != null) {
                path = path.getRoot().relativize(path);
                fullPath = storageRoot.resolve(path).normalize();
            } else {
                fullPath = root.resolve(path).normalize();
            }
        } catch (InvalidPathException e) {
            throw new FilePathInvalidException(pathStr, e);
        }

        if (!fullPath.startsWith(storageRoot)) {
            throw new FilePathInvalidException(pathStr);
        }

        return fullPath;
    }
}
