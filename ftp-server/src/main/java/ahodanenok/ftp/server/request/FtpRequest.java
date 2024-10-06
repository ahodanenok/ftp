package ahodanenok.ftp.server.request;

public interface FtpRequest {

    FtpSession getSession();

    boolean hasArgument(int pos);

    String getArgument(int pos);
}
