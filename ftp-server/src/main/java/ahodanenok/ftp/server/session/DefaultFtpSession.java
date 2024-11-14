package ahodanenok.ftp.server.session;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.connection.ControlConnection;
import ahodanenok.ftp.server.response.DefaultResponseWriter;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.security.AnonymousUser;
import ahodanenok.ftp.server.security.User;
import ahodanenok.ftp.server.transfer.DataType;
import ahodanenok.ftp.server.transfer.StructureType;
import ahodanenok.ftp.server.transfer.TransferMode;

public final class DefaultFtpSession implements FtpSession {

    private final ControlConnection controlConnection;
    private final DataConnection dataConnection;
    private final ResponseWriter responseWriter;
    private DataType dataType = DataType.ASCII;
    private StructureType structureType = StructureType.FILE;
    private TransferMode transferMode = TransferMode.STREAM;
    private String currentDirectory = "";
    private User user = AnonymousUser.INSTANCE;

    public DefaultFtpSession(ControlConnection controlConnection, DataConnection dataConnection) throws IOException {
        this.controlConnection = controlConnection;
        this.dataConnection = dataConnection;
        this.responseWriter = new DefaultResponseWriter(controlConnection.getOutputStream());
    }

    @Override
    public boolean isAuthenticated() {
        return !(user instanceof AnonymousUser);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        Objects.requireNonNull(user, "User can't be null");
        this.user = user;
    }

    @Override
    public String getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public void setCurrentDirectory(String path) {
        this.currentDirectory = path;
    }

    @Override
    public ControlConnection getControlConnection() {
        return controlConnection;
    }

    @Override
    public DataConnection getDataConnection() {
        return dataConnection;
    }

    @Override
    public ResponseWriter getResponseWriter() {
        return responseWriter;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(DataType dataType) {
        Objects.requireNonNull(dataType, "Data type can't be null");
        this.dataType = dataType;
    }

    @Override
    public StructureType getStructureType() {
        return structureType;
    }

    @Override
    public void setStructureType(StructureType structureType) {
        Objects.requireNonNull(structureType, "Structure type can't be null");
        this.structureType = structureType;
    }

    @Override
    public TransferMode getTransferMode() {
        return transferMode;
    }

    @Override
    public void setTransferMode(TransferMode transferMode) {
        Objects.requireNonNull(transferMode, "Transfer mode can't be null");
        this.transferMode = transferMode;
    }
}
