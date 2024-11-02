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

// NLST
// 125, 150
//     226, 250
//     425, 426, 451
// 450
// 500, 501, 502, 421, 530

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

        String path;
        if (request.hasArgument(0)) {
            path = request.getArgument(0);
        } else {
            path = session.getCurrentDirectory();
        }

        // todo: check valid path
        // todo: check path exists
        Stream<String> names = storage.names(path);

        // todo: could be written not only in ASCII, support EBCDIC (set by TYPE command)
        String result = names.collect(Collectors.joining("\r\n", "", "\r\n"));
        ByteArrayInputStream in = new ByteArrayInputStream(result.getBytes("US-ASCII"));

        DataSender dataSender = dataSenderFactory.createSender(null, null, null);
        dataSender.send(in, new FtpCommandDataSendContext(session, execution));
    }
}
