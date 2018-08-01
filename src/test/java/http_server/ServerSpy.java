package http_server;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ServerSpy extends Server {

    private RequestParser requestParser = new RequestParser();
    private ResponseMaker responseMaker = new ResponseMaker();
    private List<Boolean> possibleOptions = new LinkedList<>(Arrays.asList(true, false));

    public ServerSpy(String host, int port) {
        super(host, port);
    }

    public void run(ServerSocketManager socketManager) {
        while (isServerRunning()) {
            ClientSocket clientSocket = socketManager.accept();
            String request = readFromSocketStream(clientSocket);
            ByteArrayOutputStream response = responseMaker.buildWholeResponse(request);
            byte[] newResponse = response.toByteArray();
            writeResponseToRequest(clientSocket, newResponse);
            clientSocket.close();
        }
    }

    public boolean isServerRunning() {
        return possibleOptions.remove(0);
    }

}