package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.Socket;

public final class TcpSocketDataConnection implements DataConnection {

    private Socket socket;
    private final InetAddress localHost;
    private final DataPorts localPorts;
    private InetAddress remoteHost;
    private int remotePort;

    public TcpSocketDataConnection(InetAddress remoteHost, int remotePort, InetAddress localHost, DataPorts localPorts) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.localHost = localHost;
        this.localPorts = localPorts;
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
    public void setRemoteHostPort(InetAddress host, int port) throws IOException {
        boolean wasOpened = isOpened();
        if (wasOpened) {
            close();
        }

        this.remoteHost = host;
        this.remotePort = port;
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

        int localPort = localPorts.allocate();
        if (localPort == -1) {
            throw new BindException("No available port to bind for data transfer");
        }

        try {
            this.socket = new Socket(remoteHost, remotePort, localHost, localPort);
        } finally {
            localPorts.free(localPort);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            socket.close();
        } finally {
            localPorts.free(socket.getLocalPort());
            socket = null;
        }
    }
}
