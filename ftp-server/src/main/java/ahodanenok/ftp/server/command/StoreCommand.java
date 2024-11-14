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
        if (!session.isAuthenticated()) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }
        // todo: 532 Need account for storing files.

        // todo: check exactly one argument
        if (!request.hasArgument(0)) {
            responseWriter.write(FtpReply.CODE_501);
            return;
        }

        // todo: check valid path
        // todo: check path exists
        String path = request.getArgument(0);
        OutputStream out = storage.write(path);
        // todo: 553 Requested action not taken. File name not allowed.

        // todo: 110 Restart marker reply.
        DataReceiver dataReceiver = dataReceiverFactory.createReceiver(null, null, null);
        dataReceiver.receive(out, new FtpCommandDataReceiveContext(session, execution));
        // todo: 452 Requested action not taken. Insufficient storage space in system.
        // todo: 552 Requested file action aborted. Exceeded storage allocation

        // todo: when to return "450  Requested file action not taken. File unavailable"?
        // todo: when to return "451 Requested action aborted: local error in processing."?
    }
}
