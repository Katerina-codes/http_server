package http_server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static http_server.StatusCodes.OK;

public class ServerSpy extends Server {

    private RequestParser requestParser = new RequestParser();
    private ResponseMaker responseMaker = new ResponseMaker();
    private List<Boolean> possibleOptions = new LinkedList<>(Arrays.asList(true, false));

    public ServerSpy(String host, int port) {
        super(host, port);
    }

    public void run(ServerSocketManager socketManager) {
        String response = "";
        String resourceContents = "";

        while (isServerRunning()) {
            ClientSocket clientSocket = socketManager.accept();
            String request = readFromSocketStream(clientSocket);
            String resourceRequested = requestParser.parse(request);
            String statusCode = responseMaker.checkIfResourceIsAvailable(resourceRequested);
            String typeOfRequest = requestParser.extractMethodFromRequest(request);
            if (statusCode.equals(OK.getStatusCode())) {
                resourceContents = responseMaker.returnResourceContents(resourceRequested);
                response = responseMaker.buildWholeResponse(resourceContents, resourceRequested, typeOfRequest);
            } else {
                response = responseMaker.buildWholeResponse(resourceContents, resourceRequested, typeOfRequest);
            }
            writeResponseToRequest(clientSocket, response);
            clientSocket.close();
        }
    }

    public boolean isServerRunning() {
        return possibleOptions.remove(0);
    }

}