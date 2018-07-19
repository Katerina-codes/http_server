import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ServerSocketSpy implements ServerSocketManager {

    public SocketRules clientSocketSpy;
    boolean acceptWasCalled = false;

    public ServerSocketSpy(SocketRules clientSocketSpy) {
        this.clientSocketSpy = clientSocketSpy;
    }

    public SocketRules accept() {
        acceptWasCalled = true;
        return clientSocketSpy;
    }

    public static class ClientSocketSpy implements SocketRules {
        
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
