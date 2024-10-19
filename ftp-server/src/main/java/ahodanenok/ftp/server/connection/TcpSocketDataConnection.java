package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.Socket;

public final class TcpSocketDataConnection implements DataConnection {

    private Socket socket;

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
    public boolean isOpened() {
        return socket != null && !socket.isClosed();
    }

    @Override
    public void open() {
        if (socket != null && !socket.isClosed()) {
            return; // todo: throw exception?
        }

        try {
            // todo: config
            socket = new Socket(InetAddress.getLocalHost(), 10550);
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