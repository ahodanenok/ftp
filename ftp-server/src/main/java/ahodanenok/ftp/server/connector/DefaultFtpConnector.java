package ahodanenok.ftp.server.connector;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ahodanenok.ftp.server.connection.TcpSocketControlConnection;
import ahodanenok.ftp.server.connection.TcpSocketDataConnection;
import ahodanenok.ftp.server.request.FtpCommandParser;
import ahodanenok.ftp.server.request.FtpCommandParser.CommandParseResult;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.DefaultFtpRequest;
import ahodanenok.ftp.server.request.FtpReply;
import ahodanenok.ftp.server.request.FtpRequestDispatcher;
import ahodanenok.ftp.server.request.FtpSession;;
import ahodanenok.ftp.server.request.DefaultFtpSession;

public final class DefaultFtpConnector implements FtpConnector {

    private final FtpCommandParser commandParser;
    private final FtpRequestDispatcher requestDispatcher;

    public DefaultFtpConnector(FtpCommandParser commandParser, FtpRequestDispatcher requestDispatcher) {
        this.commandParser = commandParser;
        this.requestDispatcher = requestDispatcher;
    }

    @Override
    public void activate() {
        try {
            doActivate();
        } catch (Exception e) {
            e.printStackTrace(); // todo: logging
        }
    }

    private void doActivate() throws Exception {
        // todo: make configurable
        InetAddress bindAddress = InetAddress.getLocalHost();
        // todo: allow concurrency
        ServerSocket serverSocket = new ServerSocket(10450);//, 1, bindAddress);
        Socket socket = serverSocket.accept();

        DefaultFtpSession session = new DefaultFtpSession(
            new TcpSocketControlConnection(socket), new TcpSocketDataConnection());

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), "US-ASCII"));
        while (true) {
            // todo: read manually?
            // line could be of an indefinite size and only \r\n sequence must separate commands
            String command = reader.readLine();
            CommandParseResult parseResult = commandParser.parse(command);
            if (!parseResult.success()) {
                session.getResponseWriter().write(FtpReply.CODE_500);
                continue;
            }

            FtpRequest request = new DefaultFtpRequest(session, parseResult.getName(), parseResult.getArguments());
            requestDispatcher.dispatch(request);
        }
    }
}
