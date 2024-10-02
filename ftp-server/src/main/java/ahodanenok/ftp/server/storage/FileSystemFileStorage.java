package ahodanenok.ftp.server.storage;

public final class FileSystemFileStorage implements FileStorage {

    public FileSystemFileStorage(String rootDir) {

    }

    @Override
    public Iterable<String> names(String path) {
        return null;
    }
}
