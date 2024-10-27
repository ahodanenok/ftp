package ahodanenok.ftp.server.command;

import java.io.OutputStream;
import java.io.IOException;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.transfer.receive.DataReceiver;
import ahodanenok.ftp.server.transfer.receive.DataReceiverFactory;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;

public final class StoreCommand implements FtpCommand {

// STOR
// 125, 150
//     (110)
//     226, 250
//     425, 426, 451, 551, 552
// 532, 450, 452, 553
// 500, 501, 421, 530

    private final FileStorage storage;
    private final DataReceiverFactory dataReceiverFactory;

    public StoreCommand(FileStorage storage, DataReceiverFactory dataReceiverFactory) {
        this.storage = storage;
        this.dataReceiverFactory = dataReceiverFactory;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();

        if (!request.hasArgument(0)) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        // todo: check valid path
        // todo: check path exists
        String path = request.getArgument(0);
        OutputStream out = storage.write(path);

        DataConnection dataConnection = session.getDataConnection();
        if (dataConnection.isOpened()) {
            responseWriter.write(FtpReply.CODE_125);
        } else {
            responseWriter.write(FtpReply.CODE_150);
            dataConnection.open();
        }

        DataReceiver dataReceiver = dataReceiverFactory.createReceiver(null, null, null);
        dataReceiver.setIsAborted(execution::isAborted);
        boolean success = dataReceiver.receive(dataConnection.getInputStream(), out);
        if (success) {
            responseWriter.write(FtpReply.CODE_250);
        } else {
            responseWriter.write(FtpReply.CODE_426);
        }
    }
}
