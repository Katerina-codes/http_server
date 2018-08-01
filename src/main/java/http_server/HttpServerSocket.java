package http_server;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServerSocket implements ServerSocketManager {

    private final ServerSocket socketManager;

    public HttpServerSocket(ServerSocket socketManager) {
        this.socketManager = socketManager;
    }

    public ClientSocket accept() {
        HttpSocket httpSocket = null;
        try {
            httpSocket = new HttpSocket(socketManager.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpSocket;
    }

}