package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;

public final class PrintWorkingDirectoryCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();

        if (request.getArgumentCount() > 0) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        session.getResponseWriter().write(
            FtpReply.CODE_257.getCode(),
            String.format("\"%s\"", session.getCurrentDirectory()));
    }
}
