package http_server;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Server server;
    private ServerSocketSpy.ClientSocketSpy clientSocketSpy;
    private ByteArrayOutputStream outputStream;
    private ServerSocketSpy serverSocketSpy;

    @Before
    public void setUp() {
        server = new Server("localhost", 5000);
        outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, outputStream);
        serverSocketSpy = new ServerSocketSpy(clientSocketSpy);
    }

    @Test
    public void testsAConnectionCanBeMade() {
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
        server.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getOutputStreamWasCalled);
    }

    @Test
    public void writeResponseToClientSocket() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("\n".getBytes());
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, outputStream);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        server.run(serverSocketSpy);

        assertEquals("HTTP/1.1 200 OK\n", clientSocketSpy.getOutputStreamContents());
    }

}