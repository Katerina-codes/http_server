package http_server;

import java.io.*;

public class Server {

    public String host;
    public int port;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(ServerSocketManager socketManager) {
        boolean serverRunning = true;
        while (serverRunning) {
            ClientSocket clientSocket = socketManager.accept();
            String request = readFromSocketStream(clientSocket);

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output);
            writer.println("HTTP/1.1 200 OK" + request);
            writer.flush();
            serverRunning = false;
        }
    }

    private String readFromSocketStream(ClientSocket clientSocket) {
        InputStream request = clientSocket.getInputStream();
        InputStreamReader requestReader = new InputStreamReader(request);
        BufferedReader lineReader = new BufferedReader(requestReader);
        return parseRequest(lineReader);
    }

    private String parseRequest(BufferedReader lineReader) {
        String requestContent = "";
        try {
            requestContent = lineReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestContent;
    }

}