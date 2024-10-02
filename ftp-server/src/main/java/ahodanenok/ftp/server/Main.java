package ahodanenok.ftp.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import ahodanenok.ftp.server.command.FtpCommand;
import ahodanenok.ftp.server.command.NameListCommand;
import ahodanenok.ftp.server.connection.DataWriter;
import ahodanenok.ftp.server.request.DefaultFtpRequest;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;
import ahodanenok.ftp.server.storage.FileSystemFileStorage;

public class Main {

    public static void main(String... args) throws Exception {
        FileStorage storage = new FileSystemFileStorage("D:\\ftp-storage");
        FtpCommand command = new NameListCommand(storage);
        FtpSessionImpl session = new FtpSessionImpl();
        FtpRequest request = new DefaultFtpRequest(session, "NLST", new String[] { "dir" });
        command.handle(request);
        System.out.println("response:");
        System.out.println(session.writer.toString("US-ASCII"));
    }

    static class FtpSessionImpl implements FtpSession {

        private ByteArrayOutputStream writer = new ByteArrayOutputStream();
        private DataWriter dataWriter = new DataWriter() {

            @Override
            public void write(byte[] data) throws IOException {
                writer.write(data);
            }
        };

        @Override
        public DataWriter getDataWriter() {
            return dataWriter;
        }
    }
}
