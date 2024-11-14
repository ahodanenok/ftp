package ahodanenok.ftp.server.command;

import java.net.InetAddress;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;

public final class PortCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        if (!session.isAuthenticated()) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }

        if (request.getArgumentCount() != 1) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        String hostPort = request.getArgument(0);
        String[] parts = hostPort.split(",");
        if (parts.length != 6) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        InetAddress host;
        int port;
        try {
            host = InetAddress.getByAddress(new byte[] {
                (byte) parseByte(parts[0]),
                (byte) parseByte(parts[1]),
                (byte) parseByte(parts[2]),
                (byte) parseByte(parts[3])
            });
            port = (parseByte(parts[4]) << 8) | parseByte(parts[5]);
        } catch (NumberFormatException e) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        session.getDataConnection().setRemoteHostPort(host, port);
        session.getResponseWriter().write(FtpReply.CODE_200);
    }

    // todo: move to utils?
    private int parseByte(String s) {
        int b = Integer.parseInt(s);
        if (b < 0 || b > 255) {
            throw new NumberFormatException(s);
        }

        return b;
    }
}
