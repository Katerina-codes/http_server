import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ServerSocketSpy implements ServerSocketManager {

    public Socket clientSocketSpy;
    boolean acceptWasCalled = false;

    public ServerSocketSpy(Socket clientSocketSpy) {
        this.clientSocketSpy = clientSocketSpy;
    }

    public Socket accept() {
        acceptWasCalled = true;
        return clientSocketSpy;
    }

    public static class ClientSocketSpy implements Socket {
        
        public ClientSocketSpy(ByteArrayInputStream inputStream) {
            this.inputStream = inputStream;
        }

        public boolean getInputStreamWasCalled = false;
        private InputStream inputStream;

        public InputStream getInputStream() {
            getInputStreamWasCalled = true;
            return inputStream;
        }
    }
}
