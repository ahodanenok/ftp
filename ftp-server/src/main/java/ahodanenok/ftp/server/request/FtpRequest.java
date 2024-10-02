package ahodanenok.ftp.server.request;

public interface FtpRequest {

    FtpSession getSession();

    String getArgument(int pos);
}
