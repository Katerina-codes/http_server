import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Server server;
    private ServerSocketSpy.ClientSocketSpy clientSocketSpy;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        server = new Server("localhost", 5000);
        outputStream = new ByteArrayOutputStream();
        clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(new ByteArrayInputStream("".getBytes()), outputStream);
    }

    @Test
    public void testsAConnectionCanBeMade() {
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
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getInputStreamWasCalled);
    }

    @Test
    public void readsInputFromClientSocket() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("request".getBytes());
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, outputStream);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertEquals(0, inputStream.available());
    }

    @Test
    public void createsOutputStream() {
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getOutputStreamWasCalled);
    }
}
