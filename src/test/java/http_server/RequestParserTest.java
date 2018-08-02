package http_server;

import org.junit.Before;
import org.junit.Test;

import static http_server.HttpMethods.GET;
import static http_server.HttpMethods.HEAD;
import static http_server.ResponseMaker.JPEG;
import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    private RequestParser requestParser;

    @Before
    public void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    public void returnsFileName() {
        String request = "GET /file1 HTTP/1.1";
        requestParser.parseResource(request);

        assertEquals("file1", requestParser.parseResource(request));
    }

    @Test
    public void returnsAnotherFileName() {
        String request = "HEAD /image.jpeg HTTP/1.1";
        requestParser.parseResource(request);

        assertEquals("image.jpeg", requestParser.parseResource(request));
    }

    @Test
    public void returnsDirectory() {
        String request = "HEAD / HTTP/1.1";
        requestParser.parseResource(request);

        assertEquals("/", requestParser.parseResource(request));
    }

    @Test
    public void parseHeadMethodRequest() {
        String request = "HEAD / HTTP/1.1";

        assertEquals(HEAD, requestParser.extractMethodFromRequest(request));
    }

    @Test
    public void parseGetMethodRequest() {
        String request = "GET /file1 HTTP/1.1";

        assertEquals(GET, requestParser.extractMethodFromRequest(request));
    }

    @Test
    public void parsesContentType() {
        assertEquals(JPEG, requestParser.parseContentType("image.jpeg"));
    }

}