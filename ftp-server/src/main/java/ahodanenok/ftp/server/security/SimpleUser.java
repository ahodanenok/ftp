package ahodanenok.ftp.server.security;

public final class SimpleUser implements User {

    private final String userName;

    public SimpleUser(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }
}
