package ahodanenok.ftp.server.request;

import java.util.Arrays;

public interface FtpCommandParser {

    CommandParseResult parse(String command);

    public final class CommandParseResult {

        public static CommandParseResult success(String name, String[] arguments) {
            return new CommandParseResult(true, name, arguments);
        }

        public static CommandParseResult error() {
            return new CommandParseResult(false, null, null);
        }

        private final boolean success;
        private final String name;
        private final String[] arguments;

        private CommandParseResult(boolean success, String name, String[] arguments) {
            this.success = success;
            this.name = name;
            this.arguments = arguments;
        }

        public boolean success() {
            return success;
        }

        public String getName() {
            return name;
        }

        public String[] getArguments() {
            return arguments;
        }
    }
}
