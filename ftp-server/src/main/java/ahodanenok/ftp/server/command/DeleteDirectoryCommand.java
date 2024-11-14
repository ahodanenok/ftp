package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.exception.FilePathInvalidException;
import ahodanenok.ftp.server.storage.exception.FileStorageException;

public final class DeleteDirectoryCommand implements FtpCommand {

    private final FileStorage storage;

    public DeleteDirectoryCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();
        // todo: 530 Not logged in.

        if (request.getArgumentCount() != 1) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        try {
            storage.deleteDirectory(
                session.getCurrentDirectory(), request.getArgument(0));
        } catch (FilePathInvalidException e) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        } catch (FileStorageException e) {
            responseWriter.write(FtpReply.CODE_550);
            return;
        }

        responseWriter.write(FtpReply.CODE_250);
    }
}
