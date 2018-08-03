package http_server;

import java.io.IOException;
import java.net.ServerSocket;

public class StartServer {

    public static void main(String[] args) {
        int port = 5000;
        Server server = new Server("localhost", port);
        try {
            server.run(new HttpServerSocket(new ServerSocket(port)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}