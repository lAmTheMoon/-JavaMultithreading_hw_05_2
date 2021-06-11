package hw_05_2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server implements Runnable {
    private String host;
    private int port;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress(host, port));
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer in = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(in);
                    if (bytesCount == -1) {
                        break;
                    }
                    String clientInput = new String(in.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    in.clear();
                    Matcher matcher = Pattern.compile("\\s*").matcher(clientInput);
                    String cleanClientInput = matcher.replaceAll("").trim();
                    socketChannel.write(ByteBuffer.wrap(("Server say: " + cleanClientInput)
                            .getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
