import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ServerTest {

    @Test
    public void testsAConnectionCanBeMade() {
        Server server = new Server();
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy();

        server.run(serverSocketSpy);

        assertTrue(serverSocketSpy.acceptWasCalled);
    }
}
