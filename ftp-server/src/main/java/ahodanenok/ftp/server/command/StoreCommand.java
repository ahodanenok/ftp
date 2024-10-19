package ahodanenok.ftp.server.command;

import java.io.OutputStream;
import java.io.IOException;

import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.transfer.receive.DataReceiver;
import ahodanenok.ftp.server.transfer.receive.DataReceiverFactory;
import ahodanenok.ftp.server.request.FtpReply;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.FtpSession;

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
    public void handle(FtpRequest request) throws Exception {
        FtpSession session = request.getSession();

        if (!request.hasArgument(0)) {
            session.getResponseWriter().write(FtpReply.CODE_501);
        }

        // todo: check valid path
        // todo: check path exists
        String path = request.getArgument(0);
        OutputStream out = storage.write(path);
        if (session.isDataConnectionOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_125);
        } else {
            session.getResponseWriter().write(FtpReply.CODE_150);
            session.openDataConnection();
        }

        DataReceiver dataReceiver = dataReceiverFactory.createReceiver(null, null, null);
        dataReceiver.receive(session.getDataInputStream(), out);
        session.getResponseWriter().write(FtpReply.CODE_250);
    }
}
