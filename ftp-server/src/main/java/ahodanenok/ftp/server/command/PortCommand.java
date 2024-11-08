package ahodanenok.ftp.server.command;

import java.net.InetAddress;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;

public final class PortCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        // todo: 530 Not logged in.

        // todo: check has one argument
        String hostPort = request.getArgument(0);
        String[] parts = hostPort.split(",");
        if (parts.length != 6) {
            // todo: 501 Syntax error in parameters or arguments.
        }

        InetAddress host = InetAddress.getByAddress(new byte[] {
            parseByte(parts[0]),
            parseByte(parts[1]),
            parseByte(parts[2]),
            parseByte(parts[3])
        });
        int port = (parseByte(parts[4]) << 8) | parseByte(parts[5]);

        session.getDataConnection().setRemoteHostPort(host, port);
        session.getResponseWriter().write(FtpReply.CODE_200);
    }

    // todo: move to utils?
    private byte parseByte(String s) {
        // todo: 501 Syntax error in parameters or arguments.
        return Byte.parseByte(s);
    }
}
