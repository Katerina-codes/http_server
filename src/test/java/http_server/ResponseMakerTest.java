package http_server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static http_server.Header.*;
import static http_server.StatusCodes.*;
import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

    private final String blankLine = "\n\n";
    private String space = " ";
    private ResponseMaker responseMaker;
    private String newLine = "\n";

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
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("file1"));
    }

    @Test
    public void returnsContentsOfFileOne() {
        Assert.assertArrayEquals("file1 contents".getBytes(), responseMaker.returnResourceContents("file1"));
    }

    @Test
    public void buildsWholeResponse() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + newLine +
                CLOSE_CONNECTION.getText() +
                CONTENT_TYPE.getText() + "text/plain" + blankLine +
                "file1 contents",

                responseMaker.buildWholeResponse("GET /file1 HTTP/1.1").toString());
    }

   @Test
   public void returnsFourOhFourIfFileNotFoundFromRequest() {
       assertEquals(buildResponse(
               HTTP_VERSION.getText(),
               NOT_FOUND.getStatusCode(),
               space,
               NOT_FOUND.getStatusMessage()) + newLine,

               responseMaker.buildWholeResponse("GET /no_file_here.txt HTTP/1.1").toString());
   }

    @Test
    public void returnStatusOfOkIfResourceExistsButIsEmpty() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("/"));
    }

    @Test
    public void headRequestReturnsNoMessageBody() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) +
                blankLine,

                responseMaker.buildWholeResponse("HEAD / HTTP/1.1").toString());
    }

    @Test
    public void responseToOptionsRequestContainsMethodsItSupports() {
       assertEquals(buildResponse(
               HTTP_VERSION.getText(),
               OK.getStatusCode(),
               space,
               OK.getStatusMessage()) + newLine +
               CLOSE_CONNECTION.getText() +
               METHODS_ALLOWED_FOR_TXT_FILE.getText(),

               responseMaker.buildWholeResponse("OPTIONS /file1 HTTP/1.1").toString());
    }

    @Test
    public void returnsDifferentMethodsForLogsResource() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + newLine +
                CLOSE_CONNECTION.getText() +
                METHODS_ALLOWED_FOR_LOGS.getText(),

                responseMaker.buildWholeResponse("OPTIONS /logs HTTP/1.1").toString());
    }

    @Test
    public void textFileDoesNotAllowPostMethodRequest() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                METHOD_NOT_ALLOWED.getStatusCode(),
                space,
                METHOD_NOT_ALLOWED.getStatusMessage()) + newLine +
                CLOSE_CONNECTION.getText() +
                METHODS_ALLOWED_FOR_TXT_FILE.getText(),

                responseMaker.buildWholeResponse("POST /file1 HTTP/1.1").toString());
    }

    @Test
    public void serverDoesNotAllowBogusRequest() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                METHOD_NOT_ALLOWED.getStatusCode(),
                space,
                METHOD_NOT_ALLOWED.getStatusMessage()) + newLine +
                CLOSE_CONNECTION.getText() +
                METHODS_ALLOWED_FOR_TXT_FILE.getText(),

                responseMaker.buildWholeResponse("RPZFEAXH /file1 HTTP/1.1").toString());
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