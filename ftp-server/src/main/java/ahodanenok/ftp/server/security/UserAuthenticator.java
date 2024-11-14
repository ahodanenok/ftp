package ahodanenok.ftp.server.security;

public interface UserAuthenticator {

    User authenticate(String userName);
}
