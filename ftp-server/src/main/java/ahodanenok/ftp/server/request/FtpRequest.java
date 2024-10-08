package ahodanenok.ftp.server.request;

public interface FtpRequest {

    FtpSession getSession();

    String getCommandName();

    boolean hasArgument(int pos);

    String getArgument(int pos);
}
