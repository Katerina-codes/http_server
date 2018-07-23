package http_server;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServerSocket implements ServerSocketManager {

    private final int port;

    public HttpServerSocket(int port) {
        this.port = port;
    }

    @Override
    public ClientSocket accept() {
        HttpSocket httpSocket = null;
        try {
            ServerSocket socketManager = new ServerSocket(port);
            httpSocket = new HttpSocket(socketManager.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpSocket;
    }
}
