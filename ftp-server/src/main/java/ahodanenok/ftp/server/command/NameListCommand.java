package ahodanenok.ftp.server.command;

import java.io.ByteArrayInputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.transfer.send.DataSender;
import ahodanenok.ftp.server.transfer.send.DataSenderFactory;

public final class NameListCommand implements FtpCommand {

    private final FileStorage storage;
    private final DataSenderFactory dataSenderFactory;

    public NameListCommand(FileStorage storage, DataSenderFactory dataSenderFactory) {
        this.storage = storage;
        this.dataSenderFactory = dataSenderFactory;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();
        ResponseWriter responseWriter = session.getResponseWriter();
        if (!session.isAuthenticated()) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }

        String path;
        // todo: 501 Syntax error in parameters or arguments.
        if (request.hasArgument(0)) {
            path = request.getArgument(0);
        } else {
            path = session.getCurrentDirectory();
        }

        Stream<String> names = storage.names(path);
        // todo: if path doesn't exist or path is not a file, return the empty list

        // todo: could be written not only in ASCII, support EBCDIC (set by TYPE command)
        String result = names.collect(Collectors.joining("\r\n", "", "\r\n"));
        ByteArrayInputStream in = new ByteArrayInputStream(result.getBytes("US-ASCII"));

        DataSender dataSender = dataSenderFactory.createSender(null, null, null);
        dataSender.send(in, new FtpCommandDataSendContext(session, execution));

        // todo: when to return "450 Requested file action not taken"?
        // todo: when to return "451 Requested action aborted: local error in processing."?
    }
}
