package http_server;

public class StartServer {

    public static void main(String[] args) {
        int port = 5000;
        Server server = new Server("localhost", port);
        server.run(new HttpServerSocket(port));
    }
}