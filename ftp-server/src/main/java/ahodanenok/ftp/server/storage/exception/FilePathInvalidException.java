package ahodanenok.ftp.server.storage.exception;

public final class FilePathInvalidException extends FileStorageException {

    public FilePathInvalidException(String message) {
        super(message);
    }

    public FilePathInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
