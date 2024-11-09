package ahodanenok.ftp.server.response;

import java.io.IOException;

public interface ResponseWriter {

    default void write(FtpReply reply) throws IOException {
        write(reply.getCode(), reply.getDescription());
    }

    void write(int code, String description) throws IOException;

    //
    // void writeStart(FtpReply reply);
    // void writeStart(FtpReply reply, String description);
    // void writeLine(String line);
    // void writeEnd();
}
