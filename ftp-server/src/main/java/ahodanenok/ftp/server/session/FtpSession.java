package ahodanenok.ftp.server.session;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import ahodanenok.ftp.server.connection.ControlConnection;
import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.security.User;
import ahodanenok.ftp.server.transfer.DataType;
import ahodanenok.ftp.server.transfer.StructureType;
import ahodanenok.ftp.server.transfer.TransferMode;

public interface FtpSession {

    boolean isAuthenticated();

    User getUser();

    void setUser(User user);

    String getCurrentDirectory();

    void setCurrentDirectory(String path);

    ControlConnection getControlConnection();

    DataConnection getDataConnection();

    ResponseWriter getResponseWriter();

    DataType getDataType();

    void setDataType(DataType dataType);

    StructureType getStructureType();

    void setStructureType(StructureType structureType);

    TransferMode getTransferMode();

    void setTransferMode(TransferMode transferMode);
}
