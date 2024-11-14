package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.transfer.StructureType;

public final class StructureTypeCommand implements FtpCommand {

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

        String structureCode = request.getArgument(0);
        if ("F".equalsIgnoreCase(structureCode)) {
            session.setStructureType(StructureType.FILE);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("R".equalsIgnoreCase(structureCode)) {
            session.setStructureType(StructureType.RECORD);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("P".equalsIgnoreCase(structureCode)) {
            responseWriter.write(FtpReply.CODE_504);
        } else {
            responseWriter.write(FtpReply.CODE_501);
        }
    }
}
