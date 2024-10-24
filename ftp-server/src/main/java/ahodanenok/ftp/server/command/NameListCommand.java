package ahodanenok.ftp.server.command;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.utils.IOUtils;

public final class NameListCommand implements FtpCommand {

// NLST
// 125, 150
//     226, 250
//     425, 426, 451
// 450
// 500, 501, 502, 421, 530

    private final FileStorage storage;

    public NameListCommand(FileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FtpRequest request) throws Exception {
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

        DataConnection dataConnection = session.getDataConnection();
        if (dataConnection.isOpened()) {
            responseWriter.write(FtpReply.CODE_125);
        } else {
            responseWriter.write(FtpReply.CODE_150);
            dataConnection.open();
        }

        OutputStream out = dataConnection.getOutputStream();
        names.forEach(new Consumer<>() {

            boolean first = true;

            @Override
            public void accept(String name) {
                try {
                    if (!first) {
                        IOUtils.writeAscii("\r\n", out);
                    }
                    IOUtils.writeAscii(name, out);
                    first = false;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        });

        // todo: when to send 226?
        responseWriter.write(FtpReply.CODE_250);
    }
}
