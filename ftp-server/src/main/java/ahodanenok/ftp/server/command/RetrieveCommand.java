package ahodanenok.ftp.server.command;

import java.io.InputStream;
import java.io.IOException;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.transfer.send.DataSender;
import ahodanenok.ftp.server.transfer.send.DataSenderFactory;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;

public class RetrieveCommand implements FtpCommand {

// RETR
// 125, 150
//     (110)
//     226, 250
//     425, 426, 451
// 450, 550
// 500, 501, 421, 530

    private final FileStorage storage;
    private final DataSenderFactory dataSenderFactory;

    public RetrieveCommand(FileStorage storage, DataSenderFactory dataSenderFactory) {
        this.storage = storage;
        this.dataSenderFactory = dataSenderFactory;
    }

    @Override
    public void handle(FtpRequest request) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();

        if (!request.hasArgument(0)) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        // todo: check valid path
        // todo: check path exists
        String path = request.getArgument(0);
        InputStream in = storage.read(path);

        DataConnection dataConnection = session.getDataConnection();
        if (dataConnection.isOpened()) {
            responseWriter.write(FtpReply.CODE_125);
        } else {
            responseWriter.write(FtpReply.CODE_150);
            dataConnection.open();
        }

        DataSender dataSender = dataSenderFactory.createSender(null, null, null);
        dataSender.send(in, dataConnection.getOutputStream());
        responseWriter.write(FtpReply.CODE_250);
    }
}
