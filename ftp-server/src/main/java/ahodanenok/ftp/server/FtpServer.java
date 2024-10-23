package ahodanenok.ftp.server;

import ahodanenok.ftp.server.command.NameListCommand;
import ahodanenok.ftp.server.command.PortCommand;
import ahodanenok.ftp.server.command.RetrieveCommand;
import ahodanenok.ftp.server.command.StoreCommand;
import ahodanenok.ftp.server.connector.DefaultFtpConnector;
import ahodanenok.ftp.server.connector.FtpConnector;
import ahodanenok.ftp.server.request.DefaultFtpCommandParser;
import ahodanenok.ftp.server.request.FtpCommandParser;
import ahodanenok.ftp.server.request.FtpProtocolInterpreter;
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

        FtpProtocolInterpreter protocolInterpreter = new FtpProtocolInterpreter(r -> r.run());
        protocolInterpreter.register("NLST", new NameListCommand(storage));
        protocolInterpreter.register("RETR", new RetrieveCommand(storage, dataSenderFactory));
        protocolInterpreter.register("STOR", new StoreCommand(storage, dataReceiverFactory));
        protocolInterpreter.register("PORT", new PortCommand());

        FtpConnector connector = new DefaultFtpConnector(commandParser, protocolInterpreter);
        connector.activate();
    }
}
