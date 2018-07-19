import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testsAConnectionCanBeMade() {
        Server server = new Server("localhost", 5000);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy();

        server.run(serverSocketSpy);

        assertTrue(serverSocketSpy.acceptWasCalled);
    }

    @Test
    public void serverHasAHost() {
        Server server = new Server("localhost", 5000);

        assertEquals("localhost", server.host);
    }

    @Test
    public void serverHasAPort() {
        Server server = new Server("localhost", 5000);

        assertEquals(5000, server.port);
    }
}
