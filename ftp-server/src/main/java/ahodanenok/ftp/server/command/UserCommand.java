package ahodanenok.ftp.server.command;

import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.security.User;
import ahodanenok.ftp.server.security.UserAuthenticator;
import ahodanenok.ftp.server.session.FtpSession;

public final class UserCommand implements FtpCommand {

    private final UserAuthenticator authenticator;

    public UserCommand(UserAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void handle(FtpRequest request, FtpCommandExecution execution) throws Exception {
        FtpSession session = request.getSession();

        if (request.getArgumentCount() != 1) {
            session.getResponseWriter().write(FtpReply.CODE_501);
            return;
        }

        User user = authenticator.authenticate(request.getArgument(0));
        if (user == null) {
            session.getResponseWriter().write(FtpReply.CODE_530);
            return;
        }

        session.setUser(user);
        session.getResponseWriter().write(FtpReply.CODE_230);
    }
}
