package http_server;

import org.junit.Test;

import static http_server.StatusCodes.*;
import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

    private String responseStart = "HTTP/1.1 ";
    private String space = " ";

    private String buildResponse(String responseStart, String statusCode, String space, String statusMessage) {
        return responseStart + statusCode + space + statusMessage;
    }

    @Test
    public void statusOkIfFileExists() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("file1"));
    }

    @Test
    public void returnsContentsOfFileOne() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("file1 contents", responseMaker.returnResourceContents("file1"));
    }

    @Test
    public void buildsWholeResponse() {
        ResponseMaker responseMaker = new ResponseMaker();
        String response = responseMaker.buildWholeResponse("GET /file1 HTTP/1.0");

        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + "\n" +
                        "Connection: close\n" +
                        "Content-Type: text/plain" + "\n\n" +
                "file1 contents",

                response);
    }

    @Test
    public void returnStatusFourOhFourIfFileIsNotFound() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(buildResponse(
                responseStart,
                NOT_FOUND.getStatusCode(),
                space,
                NOT_FOUND.getStatusMessage()),

                responseMaker.statusResponse("/no_file_here.txt"));
    }

    @Test
    public void returnStatusOfOkIfResourceExistsButIsEmpty() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("/"));
    }

    @Test
    public void headRequestReturnsNoMessageBody() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) +
                        "\n\n",

                responseMaker.buildWholeResponse("HEAD / HTTP/1.1"));
    }

    @Test
    public void returnsCorrectContentTypeForJpeg() {
        ResponseMaker responseMaker = new ResponseMaker();

        String fileExtension = "jpeg";
        assertEquals("image/jpeg", responseMaker.returnContentType(fileExtension));
    }

    @Test
    public void returnsCorrectContentTypeForTxt() {
        ResponseMaker responseMaker = new ResponseMaker();

        String fileExtension = "txt";
        assertEquals("text/plain", responseMaker.returnContentType(fileExtension));
    }

}