package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.exception.FileNotFoundException;
import ahodanenok.ftp.server.storage.exception.FilePathInvalidException;
import ahodanenok.ftp.server.storage.exception.FileStorageException;

public final class ChangeToParentDirectoryCommand implements FtpCommand {

    private final FileStorage storage;

    public ChangeToParentDirectoryCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();
        if (!session.isAuthenticated()) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }

        if (request.getArgumentCount() > 0) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        String newWorkingDirectory;
        try {
            newWorkingDirectory =
                storage.getParentPath(session.getCurrentDirectory());
        } catch (FileStorageException e) {
            responseWriter.write(FtpReply.CODE_550);
            return;
        }

        session.setCurrentDirectory(newWorkingDirectory);
        responseWriter.write(
            FtpReply.CODE_250.getCode(),
            String.format("directory changed to \"%s\"", session.getCurrentDirectory()));
    }
}
