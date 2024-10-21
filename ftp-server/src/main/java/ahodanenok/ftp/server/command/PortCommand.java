package ahodanenok.ftp.server.command;

import java.net.InetAddress;

import ahodanenok.ftp.server.request.FtpReply;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.FtpSession;

public final class PortCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request) throws Exception {
        FtpSession session = request.getSession();

        // todo: check has one argument
        String hostPort = request.getArgument(0);
        String[] parts = hostPort.split(",");
        if (parts.length != 6) {
            // todo: error
        }

        InetAddress host = InetAddress.getByAddress(new byte[] {
            parseByte(parts[0]),
            parseByte(parts[1]),
            parseByte(parts[2]),
            parseByte(parts[3])
        });
        int port = (parseByte(parts[4]) << 8) | parseByte(parts[5]);

        session.changeDataHostPort(host, port);
        session.getResponseWriter().write(FtpReply.CODE_200);
    }

    // todo: move to utils?
    private byte parseByte(String s) {
        // todo: on error
        return Byte.parseByte(s);
    }
}
