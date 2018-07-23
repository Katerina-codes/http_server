import java.io.*;
import java.net.Socket;

public class HttpSocket implements ClientSocket {
    private final Socket socket;

    public HttpSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() {
        InputStream input = null;
        try {
            input = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    @Override
    public OutputStream getOutputStream() {
        OutputStream output = null;
        try {
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
