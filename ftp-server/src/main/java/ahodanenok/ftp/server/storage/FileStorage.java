package ahodanenok.ftp.server.storage;

public interface FileStorage {

    Iterable<String> names(String path);
}
