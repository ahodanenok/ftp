package ahodanenok.ftp.server;

import ahodanenok.ftp.server.command.NameListCommand;
import ahodanenok.ftp.server.connector.DefaultFtpConnector;
import ahodanenok.ftp.server.connector.FtpConnector;
import ahodanenok.ftp.server.request.DefaultFtpCommandParser;
import ahodanenok.ftp.server.request.FtpCommandParser;
import ahodanenok.ftp.server.request.FtpRequestDispatcher;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.FileSystemFileStorage;

public final class FtpServer {

    public static void main(String... args) throws Exception {
        FileStorage storage = new FileSystemFileStorage("D:/ftp-storage");

        FtpCommandParser commandParser = new DefaultFtpCommandParser();

        FtpRequestDispatcher requestDispatcher = new FtpRequestDispatcher(r -> r.run());
        requestDispatcher.register("NLST", new NameListCommand(storage));

        FtpConnector connector = new DefaultFtpConnector(commandParser, requestDispatcher);
        connector.activate();
    }
}
