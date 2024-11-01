package ahodanenok.ftp.server.command;

import java.io.InputStream;
import java.io.IOException;

import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.transfer.receive.DataReceiveContext;

public final class FtpCommandDataReceiveContext implements DataReceiveContext {

    private final FtpSession session;
    private final FtpCommandExecution execution;

    public FtpCommandDataReceiveContext(FtpSession session, FtpCommandExecution execution) {
        this.session = session;
        this.execution = execution;
    }

    @Override
    public boolean isAborted() {
        return execution.isAborted();
    }

    @Override
    public void onBegin() throws IOException {
        if (session.getDataConnection().isOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_125);
        }
    }

    @Override
    public InputStream openConnection() throws IOException {
        if (!session.getDataConnection().isOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_150);
            session.getDataConnection().open();
        }

        return session.getDataConnection().getInputStream();
    }

    @Override
    public void onEnd() throws IOException {
        if (session.getDataConnection().isOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_250);
        }
    }

    @Override
    public void onAbort() throws IOException {
        session.getResponseWriter().write(FtpReply.CODE_426);
    }

    @Override
    public void closeConnection() throws IOException {
        session.getDataConnection().close();
        session.getResponseWriter().write(FtpReply.CODE_226);
    }
}
