package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.exception.FileNotFoundException;
import ahodanenok.ftp.server.storage.exception.FilePathInvalidException;
import ahodanenok.ftp.server.storage.exception.FileStorageException;

public final class DeleteCommand implements FtpCommand {

    private final FileStorage storage;

    public DeleteCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();

        if (request.getArgumentCount() != 1) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        String path = request.getArgument(0);
        try {
            storage.delete(path);
        } catch (FilePathInvalidException e) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        } catch (FileStorageException e) {
            e.printStackTrace(); // todo: log
            session.getResponseWriter().write(FtpReply.CODE_550);
            return;
        }

        session.getResponseWriter().write(FtpReply.CODE_250);
    }
}
