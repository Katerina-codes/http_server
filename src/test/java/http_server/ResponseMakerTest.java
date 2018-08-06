package http_server;

import org.junit.Before;
import org.junit.Test;

import static http_server.Header.*;
import static http_server.ResponseMaker.*;
import static http_server.StatusCodes.*;
import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

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
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()),

                responseMaker.statusResponse("file1"));
    }

    @Test
    public void buildsWholeResponse() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + NEW_LINE +
                CLOSE_CONNECTION.getText() +
                CONTENT_TYPE.getText() + TEXT_PLAIN + BLANK_LINE +
                "file1 contents",

                responseMaker.buildWholeResponse("GET /file1 HTTP/1.1").toString());
    }

   @Test
   public void returnsFourOhFourIfFileNotFoundFromRequest() {
       assertEquals(buildResponse(
               HTTP_VERSION.getText(),
               NOT_FOUND.getStatusCode(),
               space,
               NOT_FOUND.getStatusMessage()) + NEW_LINE,

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
    public void returnsListOfDirectoryContents() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) + BLANK_LINE +
                "file1" + NEW_LINE +
                "file2" + NEW_LINE +
                "image.gif" + NEW_LINE +
                "image.jpeg" + NEW_LINE +
                "image.png" + NEW_LINE +
                "partial_content.txt" + "\n" +
                "patch-content.txt" + "\n" +
                "text-file.txt",

        responseMaker.buildWholeResponse("GET / HTTP/1.1").toString());
    }

    @Test
    public void headRequestReturnsNoMessageBody() {
        assertEquals(buildResponse(
                HTTP_VERSION.getText(),
                OK.getStatusCode(),
                space,
                OK.getStatusMessage()) +
                        BLANK_LINE,

                responseMaker.buildWholeResponse("HEAD / HTTP/1.1").toString());
    }

    @Test
    public void responseToOptionsRequestContainsMethodsItSupports() {
       assertEquals(buildResponse(
               HTTP_VERSION.getText(),
               OK.getStatusCode(),
               space,
               OK.getStatusMessage()) + NEW_LINE +
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
                OK.getStatusMessage()) + NEW_LINE +
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
                METHOD_NOT_ALLOWED.getStatusMessage()) + NEW_LINE +
                CONTENT_LENGTH_ZERO.getText() +
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
                METHOD_NOT_ALLOWED.getStatusMessage()) + NEW_LINE +
                CONTENT_LENGTH_ZERO.getText() +
                CLOSE_CONNECTION.getText() +
                METHODS_ALLOWED_FOR_TXT_FILE.getText(),

                responseMaker.buildWholeResponse("RPZFEAXH /file1 HTTP/1.1").toString());
    }

    @Test
    public void returnsCorrectContentTypeForJpeg() {
        assertEquals(IMAGE_JPEG, responseMaker.returnContentType(JPEG));
    }

    @Test
    public void returnsCorrectContentTypeForTxt() {
        assertEquals(TEXT_PLAIN, responseMaker.returnContentType(TEXT));
    }

    @Test
    public void returnsCorrectContentTypeForPng() {
        assertEquals(IMAGE_PNG, responseMaker.returnContentType(PNG));
    }

    @Test
    public void returnsCorrectContentTypeForGif() {
        String fileExtension = "gif";
        assertEquals(IMAGE_GIF, responseMaker.returnContentType(fileExtension));
    }

}