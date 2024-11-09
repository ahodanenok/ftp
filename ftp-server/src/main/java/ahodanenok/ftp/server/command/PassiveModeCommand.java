package ahodanenok.ftp.server.command;

import java.net.InetAddress;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;

public final class PassiveModeCommand implements FtpCommand {

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        session.getDataConnection().setPassiveMode();
        InetAddress host = session.getDataConnection().getLocalHost();
        int port = session.getDataConnection().getLocalPort();
        byte[] address = host.getAddress();
        session.getResponseWriter().write(
            FtpReply.CODE_227.getCode(),
            String.format("Entering Passive Mode (%s,%s,%s,%s,%s,%s)",
                address[0],
                address[1],
                address[2],
                address[3],
                port >> 8,
                port & 0xFF));
    }
}
