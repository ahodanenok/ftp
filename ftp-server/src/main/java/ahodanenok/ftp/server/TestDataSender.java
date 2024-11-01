package ahodanenok.ftp.server;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class TestDataSender {

    public static void main(String... args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(10550, 50, InetAddress.getLocalHost());//getByName("localhost"));
        while (true) {
            Socket socket = serverSocket.accept();
            OutputStream out = socket.getOutputStream();
            InputStream in = new FileInputStream("D:/data.txt");
            int b;
            while ((b = in.read()) != -1) {
                Thread.sleep(1000);
                out.write(b);
                System.out.printf("sent '%c'%n", (char) b);
            }
            in.close();
            out.close();
            socket.close();
            System.out.println("!!! transfer completed");
        }
    }
}
