import java.io.InputStream;

public class ServerSocketSpy implements ServerSocketManager {

    public ClientSocketSpy clientSocketSpy;
    boolean acceptWasCalled = false;

    public Socket accept() {
        acceptWasCalled = true;
        clientSocketSpy = new ClientSocketSpy();
        return clientSocketSpy;
    }

    public class ClientSocketSpy implements Socket {

        public boolean getInputStreamWasCalled = false;

        public InputStream getInputStream() {
            getInputStreamWasCalled = true;
            return null;
        }
    }
}
