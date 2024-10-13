package ahodanenok.ftp.server.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.Socket;

public final class TcpSocketDataConnection implements DataConnection {

    private Socket socket;

    @Override
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e); // todo: error handling
        }
    }

    @Override
    public boolean isOpened() {
        return socket != null;
    }

    @Override
    public void open() {
        if (socket != null) {
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