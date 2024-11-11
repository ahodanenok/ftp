package ahodanenok.ftp.server;

import java.net.InetAddress;
import java.util.concurrent.Executors;

import ahodanenok.ftp.server.config.FtpConfig;
import ahodanenok.ftp.server.config.PropertiesFileFtpConfigProvider;
import ahodanenok.ftp.server.command.ChangeWorkingDirectoryCommand;
import ahodanenok.ftp.server.command.DataTypeCommand;
import ahodanenok.ftp.server.command.DeleteCommand;
import ahodanenok.ftp.server.command.NameListCommand;
import ahodanenok.ftp.server.command.PassiveModeCommand;
import ahodanenok.ftp.server.command.PortCommand;
import ahodanenok.ftp.server.command.RetrieveCommand;
import ahodanenok.ftp.server.command.StoreCommand;
import ahodanenok.ftp.server.command.StructureTypeCommand;
import ahodanenok.ftp.server.command.TransferModeCommand;
import ahodanenok.ftp.server.connector.DefaultFtpConnector;
import ahodanenok.ftp.server.protocol.FtpProtocolInterpreter;
import ahodanenok.ftp.server.request.DefaultFtpCommandParser;
import ahodanenok.ftp.server.request.FtpCommandParser;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.FileSystemFileStorage;
import ahodanenok.ftp.server.transfer.send.DataSenderFactory;
import ahodanenok.ftp.server.transfer.send.DefaultDataSenderFactory;
import ahodanenok.ftp.server.transfer.receive.DataReceiverFactory;
import ahodanenok.ftp.server.transfer.receive.DefaultDataReceiverFactory;

public final class FtpServer {

    public static void main(String... args) throws Exception {
        PropertiesFileFtpConfigProvider configProvider =
            new PropertiesFileFtpConfigProvider("./config.properties");
        FtpConfig config = configProvider.get();

        String storageRoot = config.getString("storage.fs.root");
        if (storageRoot == null) {
            throw new IllegalStateException("Storage root is not defined");
        }

        FileStorage storage = new FileSystemFileStorage(storageRoot);
        FtpCommandParser commandParser = new DefaultFtpCommandParser();
        DataSenderFactory dataSenderFactory = new DefaultDataSenderFactory();
        DataReceiverFactory dataReceiverFactory = new DefaultDataReceiverFactory();

        FtpProtocolInterpreter protocolInterpreter =
            new FtpProtocolInterpreter(Executors.newSingleThreadExecutor());
        protocolInterpreter.register("NLST", new NameListCommand(storage, dataSenderFactory));
        protocolInterpreter.register("RETR", new RetrieveCommand(storage, dataSenderFactory));
        protocolInterpreter.register("STOR", new StoreCommand(storage, dataReceiverFactory));
        protocolInterpreter.register("PORT", new PortCommand());
        protocolInterpreter.register("DELE", new DeleteCommand(storage));
        protocolInterpreter.register("TYPE", new DataTypeCommand());
        protocolInterpreter.register("STRU", new StructureTypeCommand());
        protocolInterpreter.register("MODE", new TransferModeCommand());
        protocolInterpreter.register("PASV", new PassiveModeCommand());
        protocolInterpreter.register("CWD", new ChangeWorkingDirectoryCommand(storage));

        DefaultFtpConnector connector = new DefaultFtpConnector(commandParser, protocolInterpreter);
        connector.setControlHost(InetAddress.getByName(config.getString("connector.control.host", "localhost")));
        connector.setControlPort(config.getInteger("connector.control.port", 21));
        connector.setDataHost(InetAddress.getByName(config.getString("connector.data.host", "localhost")));
        connector.setDataPortsRange(
            config.getInteger("connector.data.ports-range-start", 40000),
            config.getInteger("connector.data.ports-range-end", 41000));
        connector.activate();
    }
}
