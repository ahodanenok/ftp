package ahodanenok.ftp.server.request;

public final class DefaultFtpRequest implements FtpRequest {

    private final FtpSession session;
    private final String command;
    private final String[] args;

    public DefaultFtpRequest(FtpSession session, String command, String[] args) {
        this.session = session;
        this.command = command;
        this.args = args;
    }

    @Override
    public FtpSession getSession() {
        return session;
    }

    @Override
    public String getArgument(int pos) {
        return args[pos];
    }
}
