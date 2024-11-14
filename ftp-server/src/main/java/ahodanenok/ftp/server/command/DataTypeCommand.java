package ahodanenok.ftp.server.command;

import java.io.IOException;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.transfer.DataType;

public final class DataTypeCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();
        if (!session.isAuthenticated()) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }

        if (request.getArgumentCount() == 0 || request.getArgumentCount() > 2) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        String typeCode = request.getArgument(0);
        if ("A".equalsIgnoreCase(typeCode)) {
            if (!checkFormatType(request)) {
                return;
            }

            session.setDataType(DataType.ASCII);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("E".equalsIgnoreCase(typeCode)) {
            if (!checkFormatType(request)) {
                return;
            }

            session.setDataType(DataType.EBCDIC);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("I".equalsIgnoreCase(typeCode)) {
            if (request.getArgumentCount() != 1) {
                responseWriter.write(FtpReply.CODE_501);
                return;
            }

            session.setDataType(DataType.IMAGE);
            responseWriter.write(FtpReply.CODE_200);
        } else if ("L".equalsIgnoreCase(typeCode)) {
            if (request.getArgumentCount() != 2) {
                responseWriter.write(FtpReply.CODE_501);
                return;
            }

            int byteSize = readByteSize(request.getArgument(1));
            if (byteSize == -1) {
                responseWriter.write(FtpReply.CODE_501);
                return;
            } else if (byteSize != 8) {
                responseWriter.write(FtpReply.CODE_504);
                return;
            }

            session.setDataType(DataType.LOCAL);
            responseWriter.write(FtpReply.CODE_200);
        } else {
            responseWriter.write(FtpReply.CODE_501);
        }
    }

    private boolean checkFormatType(FtpRequest request) throws IOException {
        if (request.getArgumentCount() == 1) {
            return true;
        }

        String formatType = request.getArgument(1);
        if ("N".equalsIgnoreCase(formatType)) {
            return true;
        }

        if ("T".equalsIgnoreCase(formatType) || "C".equalsIgnoreCase(formatType)) {
            request.getSession().getResponseWriter().write(FtpReply.CODE_504);
        } else {
            request.getSession().getResponseWriter().write(FtpReply.CODE_501);
        }

        return false;
    }

    private int readByteSize(String str) {
        int byteSize;
        try {
            byteSize = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }

        if (byteSize < 0 || byteSize > 255) {
            return -1;
        }

        return byteSize;
    }
}
