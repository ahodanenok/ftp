package ahodanenok.ftp.server.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.Socket;

public final class TcpSocketControlConnection implements ControlConnection {

    private final Socket socket;

    public TcpSocketControlConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        // todo: impl
    }
}
