package ahodanenok.ftp.server.request;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ahodanenok.ftp.server.connection.DataConnection;
import ahodanenok.ftp.server.connection.ControlConnection;
import ahodanenok.ftp.server.connection.DefaultResponseWriter;
import ahodanenok.ftp.server.connection.ResponseWriter;

public final class DefaultFtpSession implements FtpSession {

    private final ControlConnection controlConnection;
    private final DataConnection dataConnection;
    private final ResponseWriter responseWriter;

    public DefaultFtpSession(ControlConnection controlConnection, DataConnection dataConnection) {
        this.controlConnection = controlConnection;
        this.dataConnection = dataConnection;
        this.responseWriter = new DefaultResponseWriter(controlConnection.getOutputStream());
    }

    @Override
    public String getCurrentDirectory() {
        return "";
    }

    @Override
    public boolean isDataConnectionOpened() {
        return dataConnection.isOpened();
    }

    @Override
    public void openDataConnection() {
        dataConnection.open();
    }

    @Override
    public OutputStream getDataOutputStream() {
        return dataConnection.getOutputStream();
    }

    @Override
    public ResponseWriter getResponseWriter() {
        return responseWriter;
    }
}
