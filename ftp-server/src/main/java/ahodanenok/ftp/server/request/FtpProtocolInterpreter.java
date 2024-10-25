package ahodanenok.ftp.server.request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import ahodanenok.ftp.server.command.FtpCommand;
import ahodanenok.ftp.server.response.FtpReply;
import ahodanenok.ftp.server.session.FtpSession;
import ahodanenok.ftp.server.utils.IOUtils;

public final class FtpProtocolInterpreter {

    private final Map<String, FtpCommand> commands;
    private final Executor commandExecutor;

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
            IOUtils.closeSilently(request.getSession().getDataConnection());
            IOUtils.closeSilently(request.getSession().getControlConnection());
        }
    }

    private void doProcess(FtpRequest request) throws Exception {
        String commandName = request.getCommandName().toUpperCase();
        switch (commandName) {
            case "ABOR" -> executeAbortCommand(request);
            default -> executeCommand(request, commandName);
        }
    }

    private void executeAbortCommand(FtpRequest request) {
        // todo: impl
    }

    private void executeCommand(FtpRequest request, String commandName) throws Exception {
        FtpSession session = request.getSession();
        FtpCommand command = commands.get(commandName);
        if (command == null) {
            // todo: what error?
            session.getResponseWriter().write(FtpReply.CODE_500);
            return;
        }

        commandExecutor.execute(() -> {
            try {
                command.handle(request);
            } catch (Throwable e) {
                e.printStackTrace(); // todo: log error
                IOUtils.closeSilently(request.getSession().getDataConnection());
                IOUtils.closeSilently(request.getSession().getControlConnection());
            }
        });
    }
}
