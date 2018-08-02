package http_server;

import java.io.*;

public class Server {

    public String host;
    public int port;
    private ResponseMaker responseMaker = new ResponseMaker();

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
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

    private boolean isServerRunning() {
        return true;
    }

    public void writeResponseToRequest(ClientSocket clientSocket, byte[] response) {
        OutputStream output = clientSocket.getOutputStream();
        try {
            output.write(response);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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