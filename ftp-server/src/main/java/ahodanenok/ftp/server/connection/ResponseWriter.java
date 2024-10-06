package ahodanenok.ftp.server.connection;

import java.io.IOException;

import ahodanenok.ftp.server.request.FtpReply;

public interface ResponseWriter {

    // todo: what exception?
    void write(FtpReply reply);// throws IOException;

    // void write(FtpReply reply, String description);

    //
    // void writeStart(FtpReply reply);
    // void writeStart(FtpReply reply, String description);
    // void writeLine(String line);
    // void writeEnd();
}
