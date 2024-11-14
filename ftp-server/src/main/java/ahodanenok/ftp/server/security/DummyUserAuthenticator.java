package ahodanenok.ftp.server.security;

public final class DummyUserAuthenticator implements UserAuthenticator {

    @Override
    public User authenticate(String userName) {
        if ("admin".equals(userName)) {
            return new SimpleUser("admin");
        } else if ("test".equals(userName)) {
            return new SimpleUser("test");
        } else {
            return null;
        }
    }
}
