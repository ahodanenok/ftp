package ahodanenok.ftp.server.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import ahodanenok.ftp.server.command.FtpCommand;
import ahodanenok.ftp.server.command.FtpCommandExecution;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.utils.IOUtils;

public final class FtpProtocolInterpreter {

    private final Map<String, FtpCommand> commands;
    private final Executor commandExecutor;
    private volatile FtpCommandExecutionImpl currentExecution;

    public FtpProtocolInterpreter(Executor commandExecutor) {
        this.commands = new HashMap<>();
        this.commandExecutor = commandExecutor;
    }

    public void register(String commandName, FtpCommand command) {
        String normalizedName = commandName.toUpperCase();
        if (commands.containsKey(normalizedName)) {
            throw new IllegalStateException(
                String.format("Command '%s' already registered", commandName));
        }

        commands.put(normalizedName, command);
    }

    public void process(FtpRequest request) {
        try {
            doProcess(request);
        } catch (Throwable e) {
            e.printStackTrace(); // todo: log error
            abortCurrentExecution();
            clearCurrentExecution();
            IOUtils.closeSilently(request.getSession().getDataConnection());
            IOUtils.closeSilently(request.getSession().getControlConnection());
        }
    }

    private void doProcess(FtpRequest request) throws Exception {
        // todo: 421 Service not available, closing control connection.

        String commandName = request.getCommandName().toUpperCase();
        switch (commandName) {
            case "ABOR" -> executeAbortCommand(request);
            default -> executeCommand(request, commandName);
        }
    }

    private void executeAbortCommand(FtpRequest request) throws Exception {
        FtpSession session = request.getSession();
        CountDownLatch completed;
        synchronized (this) {
            if (currentExecution == null) {
                session.getDataConnection().close();
                session.getResponseWriter().write(FtpReply.CODE_226);
                return;
            }

            completed = currentExecution.completed;
            currentExecution.aborted = true;
        }

        // todo: handle interrupted exception?
        completed.await();
        session.getDataConnection().close();
        session.getResponseWriter().write(FtpReply.CODE_226);
        clearCurrentExecution();
    }

    private void executeCommand(FtpRequest request, String commandName) throws Exception {
        FtpSession session = request.getSession();
        FtpCommand command = commands.get(commandName);
        synchronized (this) {
            if (currentExecution != null) {
                return;
            }

            if (command == null) {
                // todo: what error?
                session.getResponseWriter().write(FtpReply.CODE_500);
                return;
            }

            currentExecution = new FtpCommandExecutionImpl();
        }

        commandExecutor.execute(() -> {
            try {
                command.handle(request, currentExecution);
            } catch (Throwable e) {
                e.printStackTrace(); // todo: log error
                abortCurrentExecution();
                IOUtils.closeSilently(request.getSession().getDataConnection());
                IOUtils.closeSilently(request.getSession().getControlConnection());
            } finally {
                currentExecution.completed.countDown();
                clearCurrentExecution();
            }
        });
    }

    private void abortCurrentExecution() {
        synchronized (this) {
            if (currentExecution != null) {
                currentExecution.aborted = true;
            }
        }
    }

    private void clearCurrentExecution() {
        synchronized (this) {
            currentExecution = null;
        }
    }

    private static class FtpCommandExecutionImpl implements FtpCommandExecution {

        volatile boolean aborted;
        CountDownLatch completed = new CountDownLatch(1);

        @Override
        public boolean isAborted() {
            return aborted;
        }
    }
}
