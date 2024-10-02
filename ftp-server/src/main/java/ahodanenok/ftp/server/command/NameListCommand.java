package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.storage.FileStorage;

public final class NameListCommand implements FtpCommand {

    private final FileStorage storage;

    public NameListCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request) {

    }
}
