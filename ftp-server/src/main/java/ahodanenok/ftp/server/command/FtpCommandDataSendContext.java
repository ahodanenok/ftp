package ahodanenok.ftp.server.command;

import java.io.IOException;
import java.io.OutputStream;

import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.transfer.send.DataSendContext;

public final class FtpCommandDataSendContext implements DataSendContext {

    private final FtpSession session;
    private final FtpCommandExecution execution;

    public FtpCommandDataSendContext(FtpSession session, FtpCommandExecution execution) {
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
    public OutputStream openConnection() throws IOException {
        if (!session.getDataConnection().isOpened()) {
            session.getResponseWriter().write(FtpReply.CODE_150);
            session.getDataConnection().open();
        }

        return session.getDataConnection().getOutputStream();
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
