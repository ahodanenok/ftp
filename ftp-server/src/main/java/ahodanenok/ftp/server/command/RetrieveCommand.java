package ahodanenok.ftp.server.command;

import java.io.InputStream;
import java.io.IOException;

import ahodanenok.ftp.server.transfer.DataTransferFactory;
import ahodanenok.ftp.server.transfer.DataTransferHandler;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.request.FtpReply;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.FtpSession;

public class RetrieveCommand implements FtpCommand {

// RETR
// 125, 150
//     (110)
//     226, 250
//     425, 426, 451
// 450, 550
// 500, 501, 421, 530

    private final FileStorage storage;
    private final DataTransferFactory transferFactory;

    public RetrieveCommand(FileStorage storage, DataTransferFactory transferFactory) {
        this.storage = storage;
        this.transferFactory = transferFactory;
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

        DataTransferHandler transferHandler = transferFactory.createHandler(null, null, null);
        transferHandler.transfer(in, session.getDataOutputStream());
        session.getResponseWriter().write(FtpReply.CODE_250);
    }
}
