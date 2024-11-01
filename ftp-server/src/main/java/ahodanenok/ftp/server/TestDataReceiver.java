package ahodanenok.ftp.server;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class TestDataReceiver {

    public static void main(String... args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(10550, 50, InetAddress.getLocalHost());//getByName("localhost"));
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            int b;
            while ((b = in.read()) != -1) {
                System.out.write(b);
            }
        }
    }
}