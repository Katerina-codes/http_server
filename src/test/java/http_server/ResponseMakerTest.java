package http_server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

    @Test
    public void statusOkResponse(){
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("HTTP/1.1 200 OK", responseMaker.statusResponse("file1"));
    }

    @Test
    public void returnsContentsOfFileOne() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("file1 contents", responseMaker.returnFileContents("file1"));
    }

    @Test
    public void returnsErrorMessageIfFileDoesNotExist() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("This file does not exist!", responseMaker.returnFileContents("file20"));
    }

    @Test
    public void buildsWholeResponse() {
        ResponseMaker responseMaker = new ResponseMaker();

        String file_contents = "file1 contents";

        assertEquals("HTTP/1.1 200 OK\n\n" +
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

        assertEquals("HTTP/1.1 200 OK", responseMaker.statusResponse("/"));
    }

    @Test
    public void headRequestReturnsNoMessageBody() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("HTTP/1.1 200 OK\n" +
                "Content-Length: 0\n\n", responseMaker.buildWholeResponse("Directory requested. Directory exists but is empty", "/", "HEAD"));
    }

}