public class Server {

    public String host;

    public Server(String host) {
        this.host = host;
    }

    public void run(ServerSocketManager socketManager) {
        socketManager.accept();
    }

}
