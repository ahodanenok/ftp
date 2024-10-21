package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.Socket;

public final class TcpSocketDataConnection implements DataConnection {

    private Socket socket;
    private InetAddress host;
    private int port;

    public TcpSocketDataConnection() {
        try {
            this.host = InetAddress.getLocalHost();
            this.port = 10550;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getInputStream() {
        try {
            // todo: wrap input stream to handle close?
            return socket.getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e); // todo: error handling
        }
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            // todo: wrap output stream to handle close?
            return socket.getOutputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e); // todo: error handling
        }
    }

    @Override
    public void setHostPort(InetAddress host, int port) {
        boolean wasOpened = isOpened();
        if (wasOpened) {
            close();
        }

        this.host = host;
        this.port = port;
        if (wasOpened) {
            open();
        }
    }

    @Override
    public boolean isOpened() {
        return socket != null && !socket.isClosed();
    }

    @Override
    public void open() {
        if (socket != null && !socket.isClosed()) {
            return; // todo: throw exception?
        }

        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            throw new RuntimeException(e); // todo: error handling
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            throw new UncheckedIOException(e); // todo: error handling
        }
    }
}