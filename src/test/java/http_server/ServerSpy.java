package http_server;

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
            String fileRequested = requestParser.parse(request);
            String fileContents = responseMaker.returnFileContents(fileRequested);
            String response = responseMaker.buildWholeResponse(fileContents, fileRequested);
            writeResponseToRequest(clientSocket, response);
            clientSocket.close();
        }
    }

    public boolean isServerRunning() {
        return possibleOptions.remove(0);
    }
}