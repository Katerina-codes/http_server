package http_server;

import java.io.*;

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
        while (isServerRunning()) {
            ClientSocket clientSocket = socketManager.accept();
            String request = readFromSocketStream(clientSocket);
            String fileRequested = requestParser.parse(request);
            String fileContents = responseMaker.returnFileContents(fileRequested);
            String response = responseMaker.buildWholeResponse(fileContents);
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
        return requestParser.parseRequest(lineReader);
    }

}