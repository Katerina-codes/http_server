package http_server;

import org.junit.Assert;
import org.junit.Test;

public class FileReaderTest {

    @Test
    public void returnsContentsOfFileOne() {
        FileReader fileReader = new FileReader();

        Assert.assertArrayEquals("file1 contents".getBytes(), fileReader.returnResourceContents("file1"));
    }

}