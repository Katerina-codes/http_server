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
}