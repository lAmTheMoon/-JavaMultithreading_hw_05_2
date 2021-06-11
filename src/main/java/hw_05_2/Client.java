package hw_05_2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements Runnable {
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        InetSocketAddress socketAddress = new InetSocketAddress(host, port);
        try (Scanner sc = new Scanner(System.in);
             final SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(socketAddress);
            final ByteBuffer in = ByteBuffer.allocate(2 << 10);
            while (true) {
                System.out.println("Пишите! Больше пробелов - больше удачи! А для выхода напишите end.");
                String input = sc.nextLine();
                if ("end".equals(input)) {
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(input.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(in);
                System.out.println(new String(in.array(), 0, bytesCount, StandardCharsets.UTF_8));
                in.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
