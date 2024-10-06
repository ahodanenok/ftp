package ahodanenok.ftp.server.request;

public enum FtpReply {

    CODE_125(125, "Data connection already open; transfer starting."),
    CODE_150(150, "File status okay; about to open data connection."),
    CODE_226(226, "Closing data connection."),
    CODE_250(250, "Requested file action okay, completed."),
    CODE_501(501, "Syntax error in parameters or arguments.");

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