package ahodanenok.ftp.server;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class TestDataSenderActive {

    public static void main(String... args) throws Exception {
        Socket socket = new Socket(InetAddress.getByName("localhost"), 10500);
        OutputStream out = socket.getOutputStream();
        InputStream in = new FileInputStream("D:/data.txt");
        int b;
        while ((b = in.read()) != -1) {
            Thread.sleep(100);
            out.write(b);
            System.out.printf("sent '%c'%n", (char) b);
        }
        in.close();
        out.close();
        socket.close();
        System.out.println("!!! transfer completed");
    }
}
