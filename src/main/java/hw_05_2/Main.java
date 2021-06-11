package hw_05_2;

public class Main {
    public static void main(String[] args) {
        int port = 23444;
        String host = "127.0.0.1";

        new Thread(new Server(host, port)).start();
        new Thread(new Client(host, port)).start();
    }
}