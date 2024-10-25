package ahodanenok.ftp.server.session;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.connection.ControlConnection;
import ahodanenok.ftp.server.response.DefaultResponseWriter;
import ahodanenok.ftp.server.response.ResponseWriter;

public final class DefaultFtpSession implements FtpSession {

    private final ControlConnection controlConnection;
    private final DataConnection dataConnection;
    private final ResponseWriter responseWriter;

    public DefaultFtpSession(ControlConnection controlConnection, DataConnection dataConnection) throws IOException {
        this.controlConnection = controlConnection;
        this.dataConnection = dataConnection;
        this.responseWriter = new DefaultResponseWriter(controlConnection.getOutputStream());
    }

    @Override
    public String getCurrentDirectory() {
        return "";
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
}
