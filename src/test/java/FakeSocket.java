import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;

public class FakeSocket extends Socket {

    private final ByteArrayInputStream inputStream;

    public FakeSocket(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}