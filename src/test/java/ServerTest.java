import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testsAConnectionCanBeMade() {
        Server server = new Server("localhost", 5000);
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertTrue(serverSocketSpy.acceptWasCalled);
    }

    @Test
    public void hasAHost() {
        Server server = new Server("localhost", 5000);

        assertEquals("localhost", server.host);
    }

    @Test
    public void hasAPort() {
        Server server = new Server("localhost", 5000);

        assertEquals(5000, server.port);
    }

    @Test
    public void createsInputSteam() {
        Server server = new Server("localhost", 5000);
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getInputStreamWasCalled);
    }

    @Test
    public void readsInputFromClientSocket() {
        Server server = new Server("localhost", 5000);
        ByteArrayInputStream inputStream = new ByteArrayInputStream("request".getBytes());
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, new ByteArrayOutputStream());
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertEquals(0, inputStream.available());
    }

    @Test
    public void createsOutputStream() {
        Server server = new Server("localhost", 5000);
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getOutputStreamWasCalled);
    }
}
