import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

        private InputStream inputStream;
        private ByteArrayOutputStream outputStream;
        public boolean getOutputStreamWasCalled = false;
        public boolean getInputStreamWasCalled = false;

        public ClientSocketSpy(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }


        public InputStream getInputStream() {
            getInputStreamWasCalled = true;
            return inputStream;
        }

        public OutputStream getOutputStream() {
            getOutputStreamWasCalled = true;
            return null;
        }
    }
}
