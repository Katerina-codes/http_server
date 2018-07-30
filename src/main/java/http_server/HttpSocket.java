package http_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpSocket implements ClientSocket {
    private final Socket socket;

    public HttpSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() {
        InputStream input = null;
        try {
            input = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public OutputStream getOutputStream() {
        OutputStream output = null;
        try {
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}