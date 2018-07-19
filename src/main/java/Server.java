import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Server {

    public String host;
    public int port;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(ServerSocketManager socketManager) {
        SocketRules clientSocket = socketManager.accept();
        readFromSocketStream(clientSocket);
        clientSocket.getOutputStream();
    }

    private void readFromSocketStream(SocketRules clientSocket) {
        InputStream request = clientSocket.getInputStream();
        InputStreamReader requestReader = new InputStreamReader(request);
        BufferedReader lineReader = new BufferedReader(requestReader);
        try {
            lineReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
