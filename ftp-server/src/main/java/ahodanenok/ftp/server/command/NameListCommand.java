package ahodanenok.ftp.server.command;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ahodanenok.ftp.server.connection.DataWriter;
import ahodanenok.ftp.server.request.FtpReply;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;

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

        String path;
        if (request.hasArgument(0)) {
            path = request.getArgument(0);
        } else {
            path = session.getCurrentDirectory();
        }

        // todo: check valid path
        // todo: check path exists
        Stream<String> names = storage.names(path);
        if (session.isDataConnectionOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_125);
        } else {
            session.getResponseWriter().write(FtpReply.CODE_150);
            session.openDataConnection();
        }

        //DataWriter dataWriter = session.getDataWriter();
        OutputStream out = session.getDataOutputStream();
        names.forEach(new Consumer<>() {

            boolean first = true;

            @Override
            public void accept(String name) {
                try {
                    if (!first) {
                        // dataWriter.newLine();
                        out.write('\r');
                        out.write('\n');
                    }
                    //dataWriter.write(name);
                    out.write(name.getBytes("US-ASCII"));
                    first = false;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        });

        // todo: when to send 226?
        session.getResponseWriter().write(FtpReply.CODE_250);
    }
}
