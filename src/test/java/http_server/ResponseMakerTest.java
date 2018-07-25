package http_server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseMakerTest {

    @Test
    public void statusOkResponse(){
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("HTTP/1.1 200 OK", responseMaker.statusResponse());
    }

    @Test
    public void returnsContentsOfFileOne() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals("file1 contents", responseMaker.returnFileContents("file1"));
    }

    @Test
    public void returnsNullIfFileDoesNotExist() {
        ResponseMaker responseMaker = new ResponseMaker();

        assertEquals(null, responseMaker.returnFileContents("file20"));
    }

    @Test
    public void buildsWholeResponse() {
        ResponseMaker responseMaker = new ResponseMaker();

        String file_contents = "file1 contents";

        assertEquals("HTTP/1.1 200 OK\n" +
                 file_contents + "\n", responseMaker.buildWholeResponse(file_contents));
    }

}
