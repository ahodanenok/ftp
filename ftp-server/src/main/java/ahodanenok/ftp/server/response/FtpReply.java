package ahodanenok.ftp.server.response;

public enum FtpReply {

    CODE_125(125, "Data connection already open; transfer starting."),
    CODE_150(150, "File status okay; about to open data connection."),
    CODE_200(200, "Command okay."),
    CODE_220(220, "Service ready for new user."),
    CODE_226(226, "Closing data connection."),
    CODE_227(227, "Entering Passive Mode (h1,h2,h3,h4,p1,p2)."),
    CODE_257(257, "\"PATHNAME\" created."),
    CODE_250(250, "Requested file action okay, completed."),
    CODE_425(425, "Can't open data connection."),
    CODE_426(426, "Connection closed; transfer aborted."),
    CODE_450(450, "Requested file action not taken."),
    CODE_500(500, "Syntax error, command unrecognized."),
    CODE_501(501, "Syntax error in parameters or arguments."),
    CODE_504(504, "Command not implemented for that parameter."),
    CODE_550(550, "Requested action not taken.");

    private final int code;
    private final String description;

    FtpReply(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
