package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.exception.FileNotFoundException;
import ahodanenok.ftp.server.storage.exception.FilePathInvalidException;
import ahodanenok.ftp.server.storage.exception.FileStorageException;

public final class ChangeWorkingDirectoryCommand implements FtpCommand {

    private final FileStorage storage;

    public ChangeWorkingDirectoryCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();
        // todo: 530 Not logged in.

        if (request.getArgumentCount() != 1) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        String newWorkingDirectory;
        try {
            newWorkingDirectory = storage.resolvePath(
                session.getCurrentDirectory(), request.getArgument(0));
        } catch (FilePathInvalidException e) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        } catch (FileNotFoundException e) {
            responseWriter.write(FtpReply.CODE_550);
            return;
        } catch (FileStorageException e) {
            responseWriter.write(FtpReply.CODE_550);
            return;
        }

        session.setCurrentDirectory(newWorkingDirectory);
        responseWriter.write(FtpReply.CODE_250);
    }
}
