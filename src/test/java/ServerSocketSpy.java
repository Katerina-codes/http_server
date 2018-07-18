import java.net.Socket;

public class ServerSocketSpy implements ServerSocketManager {

    boolean acceptWasCalled = false;

    public Socket accept() {
        acceptWasCalled = true;
        return null;
    }
}
