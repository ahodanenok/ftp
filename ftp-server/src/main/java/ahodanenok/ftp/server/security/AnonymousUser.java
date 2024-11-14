package ahodanenok.ftp.server.security;

public final class AnonymousUser implements User {

    public static final AnonymousUser INSTANCE = new AnonymousUser();

    public String getUserName() {
        return "<anonymous>";
    }
}
