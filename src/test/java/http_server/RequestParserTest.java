package http_server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void returnsFileName() {
        RequestParser requestParser = new RequestParser();

        String request = "GET /file1 HTTP/1.1";
        requestParser.parse(request);

        assertEquals("file1", requestParser.parse(request));
    }

    @Test
    public void returnsAnotherFileName() {
        RequestParser requestParser = new RequestParser();

        String request = "GET /file2 HTTP/1.1";
        requestParser.parse(request);

        assertEquals("file2", requestParser.parse(request));
    }

    @Test
    public void parseHeadMethodRequest() {
        RequestParser requestParser = new RequestParser();

        String request = "HEAD / HTTP/1.1";

        assertEquals("HEAD", requestParser.extractMethodFromRequest(request));
    }

    @Test
    public void parseGetMethodRequest() {
        RequestParser requestParser = new RequestParser();

        String request = "GET /file1 HTTP/1.1";

        assertEquals("GET", requestParser.extractMethodFromRequest(request));
    }

    @Test
    public void parsesContentType() {
        RequestParser requestParser = new RequestParser();

        String request = "GET /image.jpeg HTTP/1.1";

        assertEquals("jpeg", requestParser.parseContentType(request));
    }

}