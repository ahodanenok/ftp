package ahodanenok.ftp.server.request;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ahodanenok.ftp.server.connection.DataWriter;
import ahodanenok.ftp.server.connection.ResponseWriter;

public final class DefaultFtpSession implements FtpSession {

    private ResponseWriter responseWriter;
    private Socket dataSocket;
    private OutputStream dataOutputStream;

    @Override
    public String getCurrentDirectory() {
        return "";
    }

    @Override
    public boolean isDataConnectionOpened() {
        return false;
    }

    @Override
    public void openDataConnection() {
        try {
            dataSocket = new Socket(InetAddress.getLocalHost(), 10550);
            dataOutputStream = dataSocket.getOutputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    @Override
    public DataWriter getDataWriter() {
        return null;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }

    @Override
    public ResponseWriter getResponseWriter() {
        return responseWriter;
    }
}
