package http_server;

import org.junit.Test;

import static http_server.StatusCodes.DIRECTORY_WITH_NO_CONTENT;
import static http_server.StatusCodes.FILE_NOT_FOUND;
import static http_server.StatusCodes.REQUEST_HAS_SUCCEEDED;
import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

    @Test
    public void statusOkResponse(){
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(REQUEST_HAS_SUCCEEDED.getMessage(), responseMaker.statusResponse("file1"));
    }

    @Test
    public void returnsContentsOfFileOne() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("file1 contents", responseMaker.returnFileContents("file1"));
    }

    @Test
    public void returnsErrorMessageIfFileDoesNotExist() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(FILE_NOT_FOUND.getMessage(), responseMaker.returnFileContents("file20"));
    }

    @Test
    public void buildsWholeResponse() {
        ResponseMaker responseMaker = new ResponseMaker();

        String file_contents = "file1 contents";

        assertEquals(REQUEST_HAS_SUCCEEDED.getMessage() + "\n\n" +
                 file_contents, responseMaker.buildWholeResponse(file_contents, "file1", "GET"));
    }

    @Test
    public void returnStatusFourOhFourIfFileIsNotFound() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("HTTP/1.1 404 Not Found", responseMaker.statusResponse("/no_file_here.txt"));
    }

    @Test
    public void returnStatusOfOkIfResourceExistsButIsEmpty() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(REQUEST_HAS_SUCCEEDED.getMessage(), responseMaker.statusResponse("/"));
    }

    @Test
    public void headRequestReturnsNoMessageBody() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(REQUEST_HAS_SUCCEEDED.getMessage() + "\n" +
                "Content-Length: 0\n\n", responseMaker.buildWholeResponse(DIRECTORY_WITH_NO_CONTENT.getMessage(), "/", "HEAD"));
    }

}