import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testsAConnectionCanBeMade() {
        Server server = new Server("localhost");
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy();

        server.run(serverSocketSpy);

        assertTrue(serverSocketSpy.acceptWasCalled);
    }

    @Test
    public void serverHasAHost() {
        Server server = new Server("localhost");

        assertEquals("localhost", server.host);
    }
}
