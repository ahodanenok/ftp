package ahodanenok.ftp.server.request;

import java.util.Arrays;

public final class DefaultFtpCommandParser implements FtpCommandParser {

    @Override
    public CommandParseResult parse(String command) {
        command = command.trim();
        if (command.length() == 0) {
            return CommandParseResult.error();
        }

        // todo: refuse to parse too long command?
        String[] parts = command.split("[ ]+");
        if (parts.length == 0) {
            return CommandParseResult.error();
        }

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        return CommandParseResult.success(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }
}
