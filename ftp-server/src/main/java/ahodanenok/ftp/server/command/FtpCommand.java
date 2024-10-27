package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;

public interface FtpCommand {

    void handle(FtpRequest request, FtpCommandExecution execution) throws Exception;
}
