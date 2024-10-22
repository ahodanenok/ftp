package ahodanenok.ftp.server.request;

import ahodanenok.ftp.server.session.FtpSession;

public interface FtpRequest {

    FtpSession getSession();

    String getCommandName();

    boolean hasArgument(int pos);

    String getArgument(int pos);
}
