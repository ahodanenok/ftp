package ahodanenok.ftp.server.command;

import java.io.InputStream;
import java.io.IOException;

import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.transfer.send.DataSender;
import ahodanenok.ftp.server.transfer.send.DataSenderFactory;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
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

        if (!request.hasArgument(0)) {
            session.getResponseWriter().write(FtpReply.CODE_501);
        }

        // todo: check valid path
        // todo: check path exists
        String path = request.getArgument(0);
        InputStream in = storage.read(path);
        if (session.isDataConnectionOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_125);
        } else {
            session.getResponseWriter().write(FtpReply.CODE_150);
            session.openDataConnection();
        }

        DataSender dataSender = dataSenderFactory.createSender(null, null, null);
        dataSender.send(in, session.getDataOutputStream());
        session.getResponseWriter().write(FtpReply.CODE_250);
    }
}
