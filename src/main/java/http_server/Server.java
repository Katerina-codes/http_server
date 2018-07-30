package http_server;

import java.io.*;

import static http_server.StatusCodes.OK;

public class Server {

    public String host;
    public int port;
    private RequestParser requestParser = new RequestParser();
    private ResponseMaker responseMaker = new ResponseMaker();

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
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

    private boolean isServerRunning() {
        return true;
    }

    public void writeResponseToRequest(ClientSocket clientSocket, String response) {
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output);
        writer.print(response);
        writer.flush();
        writer.close();
    }

    public String readFromSocketStream(ClientSocket clientSocket) {
        InputStream request = clientSocket.getInputStream();
        InputStreamReader requestReader = new InputStreamReader(request);
        BufferedReader lineReader = new BufferedReader(requestReader);
        String requestContent = "";
        try {
            requestContent = lineReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestContent;
    }

}