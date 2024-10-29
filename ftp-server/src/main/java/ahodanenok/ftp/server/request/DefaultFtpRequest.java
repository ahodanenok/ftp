package ahodanenok.ftp.server.request;

import ahodanenok.ftp.server.session.FtpSession;

public final class DefaultFtpRequest implements FtpRequest {

    private final FtpSession session;
    private final String commandName;
    private final String[] args;

    public DefaultFtpRequest(FtpSession session, String commandName, String[] args) {
        this.session = session;
        this.commandName = commandName;
        this.args = args;
    }

    @Override
    public FtpSession getSession() {
        return session;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public int getArgumentCount() {
        return args.length;
    }

    @Override
    public boolean hasArgument(int pos) {
        return args.length > pos;
    }

    @Override
    public String getArgument(int pos) {
        return args[pos];
    }
}
