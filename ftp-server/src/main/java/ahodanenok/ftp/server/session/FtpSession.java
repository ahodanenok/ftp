package ahodanenok.ftp.server.session;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import ahodanenok.ftp.server.connection.ControlConnection;
import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.response.ResponseWriter;
import ahodanenok.ftp.server.transfer.DataType;

public interface FtpSession {

    String getCurrentDirectory();

    ControlConnection getControlConnection();

    DataConnection getDataConnection();

    ResponseWriter getResponseWriter();

    DataType getDataType();

    void setDataType(DataType dataType);
}
