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

        return CommandParseResult.success(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
    }
}
