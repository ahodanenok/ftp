package ahodanenok.ftp.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import ahodanenok.ftp.server.command.*;
import ahodanenok.ftp.server.connection.*;
import ahodanenok.ftp.server.request.*;
import ahodanenok.ftp.server.storage.*;

public class Main {

    public static void main(String... args) throws Exception {
        FileStorage storage = new FileSystemFileStorage("D:\\ftp-storage");
        FtpSessionImpl session = new FtpSessionImpl();
        // new NameListCommand(storage).handle(new DefaultFtpRequest(session, "NLST", new String[] { "dir" }));
        new RetrieveCommand(storage).handle(new DefaultFtpRequest(session, "RETR", new String[] { "dir/a.txt" }));
        System.out.println("response:");
        System.out.print(session._responseWriter.toString("US-ASCII"));
        System.out.print(session._dataWriter.toString("US-ASCII"));
        System.out.println();
    }

    static class FtpSessionImpl implements FtpSession {

        private ByteArrayOutputStream _dataWriter = new ByteArrayOutputStream();
        private DataWriter dataWriter = new DataWriter() {

            @Override
            public void write(byte[] data) { //throws IOException {
                try {
                    _dataWriter.write(data);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void write(byte[] data, int offset, int length) { //throws IOException {
                _dataWriter.write(data, offset, length);
            }

            @Override
            public void write(String data) { //throws IOException {
                try {
                    _dataWriter.write(data.getBytes("US-ASCII"));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void newLine() { //throws IOException {
                _dataWriter.write('\r');
                _dataWriter.write('\n');
            }
        };

        private ByteArrayOutputStream _responseWriter = new ByteArrayOutputStream();
        private ResponseWriter responseWriter = new ResponseWriter() {

            @Override
            public void write(FtpReply reply) {// throws IOException {
                try {
                    _responseWriter.write((reply.getCode() + "").getBytes("US-ASCII"));
                    _responseWriter.write(' ');
                    _responseWriter.write(reply.getDescription().getBytes("US-ASCII"));
                    _responseWriter.write('\r');
                    _responseWriter.write('\n');
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        };

        @Override
        public DataWriter getDataWriter() {
            return dataWriter;
        }

        @Override
        public ResponseWriter getResponseWriter() {
            return responseWriter;
        }

        @Override
        public String getCurrentDirectory() {
            return "home";
        }

        @Override
        public boolean isDataConnectionOpened() {
            return true;
        }

        @Override
        public void openDataConnection() { }
    }
}
