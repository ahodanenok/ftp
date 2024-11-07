package ahodanenok.ftp.server.config;

public interface FtpConfig {

    String getString(String name);

    String getString(String name, String defaultValue);

    Integer getInteger(String name);

    Integer getInteger(String name, Integer defaultValue);
}
