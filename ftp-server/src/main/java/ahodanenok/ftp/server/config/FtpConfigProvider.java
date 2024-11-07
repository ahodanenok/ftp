package ahodanenok.ftp.server.config;

public interface FtpConfigProvider {

    FtpConfig get() throws FtpConfigException;
}
