package http_server;

public class StartServer {

    public static void main(String[] args) {
        Server server = new Server("localhost", 5000);
        server.run(new HttpServerSocket(5000));
    }
}