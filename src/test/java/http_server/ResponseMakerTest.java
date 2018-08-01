package http_server;

import org.junit.Before;
import org.junit.Test;

import static http_server.StatusCodes.*;
import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

    private String responseStart = "HTTP/1.1 ";
    private String space = " ";
    private ResponseMaker responseMaker;

    @Before
    public void setUp() {
        responseMaker = new ResponseMaker();
    }

    private String buildResponse(String responseStart, String statusCode, String space, String statusMessage) {
        return responseStart + statusCode + space + statusMessage;
    }

    @Test
    public void statusOkIfFileExists() {
        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("file1"));
    }

    @Test
    public void returnsContentsOfFileOne() {
        assertEquals("file1 contents", responseMaker.returnResourceContents("file1"));
    }

    @Test
    public void buildsWholeResponse() {

        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + "\n" +
                "Connection: close\n" +
                "Content-Type: text/plain" + "\n\n" +
                "file1 contents",

                responseMaker.buildWholeResponse("GET /file1 HTTP/1.0"));
    }

    @Test
    public void returnStatusFourOhFourIfFileIsNotFound() {
        assertEquals(buildResponse(
                responseStart,
                NOT_FOUND.getStatusCode(),
                space,
                NOT_FOUND.getStatusMessage()),

                responseMaker.statusResponse("/no_file_here.txt"));
    }

    @Test
    public void returnStatusOfOkIfResourceExistsButIsEmpty() {
        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("/"));
    }

    @Test
    public void headRequestReturnsNoMessageBody() {
        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) +
                "\n\n",

                responseMaker.buildWholeResponse("HEAD / HTTP/1.1"));
    }

    @Test
    public void responseToOptionsRequestContainsMethodsItSupports() {
       assertEquals(buildResponse(
               responseStart,
               OK.getStatusCode(),
               space,
               OK.getStatusMessage()) + "\n" +
               "Connection: close\n" +
               "Allow: GET, HEAD, OPTIONS, PUT, DELETE\n",

               responseMaker.buildWholeResponse("OPTIONS /file1 HTTP/1.1"));
    }

    @Test
    public void returnsDifferentMethodsForLogsResource() {
        assertEquals(buildResponse(
                responseStart,
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + "\n" +
                "Connection: close\n" +
                "Allow: GET, HEAD, OPTIONS\n",

                responseMaker.buildWholeResponse("OPTIONS /logs HTTP/1.1"));
    }

    @Test
    public void textFileDoesNotAllowPostMethodRequest() {
        assertEquals(buildResponse(
                responseStart,
                "405",
                space,
                "Method Not Allowed") + "\n" +
                "Connection: close\n" +
                "Allow: GET, HEAD, OPTIONS, PUT, DELETE\n",

                responseMaker.buildWholeResponse("POST /file1 HTTP/1.1"));
    }

    @Test
    public void serverDoesNotAllowBogusRequest() {
        assertEquals(buildResponse(
                responseStart,
                "405",
                space,
                "Method Not Allowed") + "\n" +
                "Connection: close\n" +
                "Allow: GET, HEAD, OPTIONS, PUT, DELETE\n",

                responseMaker.buildWholeResponse("RPZFEAXH /file1 HTTP/1.1"));
    }

    @Test
    public void returnsCorrectContentTypeForJpeg() {
        String fileExtension = "jpeg";
        assertEquals("image/jpeg", responseMaker.returnContentType(fileExtension));
    }

    @Test
    public void returnsCorrectContentTypeForTxt() {
        String fileExtension = "txt";
        assertEquals("text/plain", responseMaker.returnContentType(fileExtension));
    }

    @Test
    public void returnsCorrectContentTypeForPng() {
        String fileExtension = "png";
        assertEquals("image/png", responseMaker.returnContentType(fileExtension));
    }

    @Test
    public void returnsCorrectContentTypeForGif() {
        String fileExtension = "gif";
        assertEquals("image/gif", responseMaker.returnContentType(fileExtension));
    }

}