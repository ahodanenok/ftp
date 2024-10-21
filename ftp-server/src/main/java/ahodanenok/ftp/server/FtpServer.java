package ahodanenok.ftp.server;

import ahodanenok.ftp.server.command.NameListCommand;
import ahodanenok.ftp.server.command.PortCommand;
import ahodanenok.ftp.server.command.RetrieveCommand;
import ahodanenok.ftp.server.command.StoreCommand;
import ahodanenok.ftp.server.connector.DefaultFtpConnector;
import ahodanenok.ftp.server.connector.FtpConnector;
import ahodanenok.ftp.server.request.DefaultFtpCommandParser;
import ahodanenok.ftp.server.request.FtpCommandParser;
import ahodanenok.ftp.server.request.FtpRequestDispatcher;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.FileSystemFileStorage;
import ahodanenok.ftp.server.transfer.send.DataSenderFactory;
import ahodanenok.ftp.server.transfer.send.DefaultDataSenderFactory;
import ahodanenok.ftp.server.transfer.receive.DataReceiverFactory;
import ahodanenok.ftp.server.transfer.receive.DefaultDataReceiverFactory;

public final class FtpServer {

    public static void main(String... args) throws Exception {
        FileStorage storage = new FileSystemFileStorage("D:/ftp-storage");
        FtpCommandParser commandParser = new DefaultFtpCommandParser();
        DataSenderFactory dataSenderFactory = new DefaultDataSenderFactory();
        DataReceiverFactory dataReceiverFactory = new DefaultDataReceiverFactory();

        FtpRequestDispatcher requestDispatcher = new FtpRequestDispatcher(r -> r.run());
        requestDispatcher.register("NLST", new NameListCommand(storage));
        requestDispatcher.register("RETR", new RetrieveCommand(storage, dataSenderFactory));
        requestDispatcher.register("STOR", new StoreCommand(storage, dataReceiverFactory));
        requestDispatcher.register("PORT", new PortCommand());

        FtpConnector connector = new DefaultFtpConnector(commandParser, requestDispatcher);
        connector.activate();
    }
}
