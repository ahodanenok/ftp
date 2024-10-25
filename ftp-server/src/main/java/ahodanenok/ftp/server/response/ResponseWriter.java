package ahodanenok.ftp.server.response;

import java.io.IOException;

public interface ResponseWriter {

    void write(FtpReply reply) throws IOException;

    // void write(FtpReply reply, String description);

    //
    // void writeStart(FtpReply reply);
    // void writeStart(FtpReply reply, String description);
    // void writeLine(String line);
    // void writeEnd();
}
