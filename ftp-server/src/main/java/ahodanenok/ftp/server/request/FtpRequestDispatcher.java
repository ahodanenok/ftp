package ahodanenok.ftp.server.request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import ahodanenok.ftp.server.command.FtpCommand;

public final class FtpRequestDispatcher {

    private final Map<String, FtpCommand> commands;
    private final Executor commandExecutor;

    public FtpRequestDispatcher(Executor commandExecutor) {
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

    public void dispatch(FtpRequest request) {
        FtpSession session = request.getSession();
        FtpCommand command = commands.get(request.getCommandName().toUpperCase());
        if (command == null) {
            session.getResponseWriter().write(FtpReply.CODE_500);
            return;
        }

        commandExecutor.execute(() -> {
            try {
                command.handle(request);
            } catch (Exception e) {
                e.printStackTrace(); // todo: logging
                session.getResponseWriter().write(FtpReply.CODE_500); // todo: what reply to send?
            }
        });
    }
}
