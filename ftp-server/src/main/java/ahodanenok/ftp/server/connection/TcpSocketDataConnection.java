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
    public InputStream getInputStream() throws IOException {
        // todo: wrap input stream to handle close?
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        // todo: wrap output stream to handle close?
        return socket.getOutputStream();
    }

    @Override
    public void setHostPort(InetAddress host, int port) throws IOException {
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
    public void open() throws IOException {
        if (isOpened()) {
            return; // todo: throw exception?
        }

        socket = new Socket(host, port);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        socket = null;
    }
}
