package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.exception.FileNotFoundException;
import ahodanenok.ftp.server.storage.exception.FilePathInvalidException;
import ahodanenok.ftp.server.storage.exception.FileStorageException;

public final class DeleteFileCommand implements FtpCommand {

    private final FileStorage storage;

    public DeleteFileCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        // todo: 530 Not logged in.

        if (request.getArgumentCount() != 1) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        String path = request.getArgument(0);
        try {
            storage.deleteFile(session.getCurrentDirectory(), path);
            // todo: when to return "450  Requested file action not taken. File unavailable"?
        } catch (FilePathInvalidException e) {
            // todo: 553 Requested action not taken. File name not allowed.
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
