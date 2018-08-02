package http_server;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static http_server.Header.CLOSE_CONNECTION;
import static http_server.Header.CONTENT_TYPE;
import static http_server.ResponseMaker.TEXT_PLAIN;
import static http_server.ResponseMaker.BLANK_LINE;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Server server;
    private ServerSocketSpy.ClientSocketSpy clientSocketSpy;
    private ByteArrayOutputStream outputStream;
    private ServerSocketSpy serverSocketSpy;
    private ByteArrayInputStream inputStream;
    private ServerSpy serverSpy;

    @Before
    public void setUp() {
        server = new Server("localhost", 5000);
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream("GET /file1 HTTP/1.0\nGET /file1 HTTP/1.0\n".getBytes());
        clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, outputStream);
        serverSocketSpy = new ServerSocketSpy(clientSocketSpy);
        serverSpy = new ServerSpy("localhost", 5000);
    }

    @Test
    public void testsAConnectionCanBeMade() {
        serverSpy.run(serverSocketSpy);

        assertTrue(serverSocketSpy.acceptWasCalled);
    }

    @Test
    public void hasAHost() {
        assertEquals("localhost", server.host);
    }

    @Test
    public void hasAPort() {
        assertEquals(5000, server.port);
    }

    @Test
    public void createsInputStream() {
        serverSpy.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getInputStreamWasCalled);
    }

    @Test
    public void readsInputFromClientSocket() {
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, outputStream);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        serverSpy.run(serverSocketSpy);

        assertEquals(0, inputStream.available());
    }

    @Test
    public void createsOutputStream() {
        serverSpy.run(serverSocketSpy);

        assertTrue(clientSocketSpy.getOutputStreamWasCalled);
    }

    @Test
    public void writeResponseToClientSocket() {
        ServerSocketSpy.ClientSocketSpy clientSocketSpy = new ServerSocketSpy.ClientSocketSpy(inputStream, outputStream);
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(clientSocketSpy);

        serverSpy.run(serverSocketSpy);

        assertEquals("HTTP/1.1 200 OK\n" +
                CLOSE_CONNECTION.getText() +
                CONTENT_TYPE.getText() + TEXT_PLAIN + BLANK_LINE +
                "file1 contents",

                clientSocketSpy.getOutputStreamContents());
    }

}