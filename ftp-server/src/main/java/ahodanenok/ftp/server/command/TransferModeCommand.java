package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.transfer.TransferMode;

public final class TransferModeCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();
        if (!session.isAuthenticated()) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }

        if (request.getArgumentCount() != 1) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        String modeCode = request.getArgument(0);
        if ("S".equalsIgnoreCase(modeCode)) {
            session.setTransferMode(TransferMode.STREAM);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("B".equalsIgnoreCase(modeCode)) {
            session.setTransferMode(TransferMode.BLOCK);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("C".equalsIgnoreCase(modeCode)) {
            session.setTransferMode(TransferMode.COMPRESSED);
            responseWriter.write(FtpReply.CODE_200);
        } else {
            responseWriter.write(FtpReply.CODE_501);
        }
    }
}
