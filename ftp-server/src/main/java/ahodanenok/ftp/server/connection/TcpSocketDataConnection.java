package ahodanenok.ftp.server.connection;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class TcpSocketDataConnection implements DataConnection {

    private Socket socket;
    private ServerSocket serverSocket;
    private final InetAddress localHost;
    private final DataPorts localPorts;
    private InetAddress remoteHost;
    private int remotePort;
    private boolean passive;

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
    public void setPassiveMode() throws IOException {
        if (passive) {
            return;
        }

        int localPort = localPorts.allocate();
        if (localPort == -1) {
            throw new BindException("No available port to bind for data transfer");
        }

        try {
            serverSocket = new ServerSocket(localPort, 1, localHost);
            passive = true;
        } finally {
            localPorts.free(localPort);
        }
    }

    @Override
    public InetAddress getLocalHost() {
        return localHost;
    }

    @Override
    public int getLocalPort() {
        if (serverSocket != null) {
            return serverSocket.getLocalPort();
        } else if (socket != null) {
            return socket.getLocalPort();
        } else {
            return -1;
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

        if (passive) {
            socket = serverSocket.accept();
            return;
        }

        int localPort = localPorts.allocate();
        if (localPort == -1) {
            throw new BindException("No available port to bind for data transfer");
        }

        try {
            socket = new Socket(remoteHost, remotePort, localHost, localPort);
        } finally {
            localPorts.free(localPort);
        }
    }

    @Override
    public void close() throws IOException {
        if (socket != null) {
            try {
                socket.close();
            } finally {
                localPorts.free(socket.getLocalPort());
                socket = null;
            }
        }
    }
}
