import org.junit.Test;

import java.io.ByteArrayInputStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testsAConnectionCanBeMade() {
        Server server = new Server("localhost", 5000);
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(new ByteArrayInputStream("".getBytes()));
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

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

    @Test
    public void serverCreatesInputSteam() {
        Server server = new Server("localhost", 5000);
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(new ByteArrayInputStream("".getBytes()));
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getInputStreamWasCalled);
    }

    @Test
    public void serverReadsInputFromClientSocket() {
        Server server = new Server("localhost", 5000);
        ByteArrayInputStream inputStream = new ByteArrayInputStream("request".getBytes());
        SocketRules clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertEquals(0, inputStream.available());
    }
}
