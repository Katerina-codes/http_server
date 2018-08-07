package http_server;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FileReaderTest {

    @Test
    public void returnsContentsOfFileOne() {
        FileReader fileReader = new FileReader();

        Assert.assertArrayEquals("file1 contents".getBytes(), fileReader.returnResourceContents("file1"));
    }

    @Test
    public void returnAllFiles() throws DirectoryNotFoundException {
        FileReader fileReader = new FileReader();

        List<String> directoryContents = asList("file1",
                "file2",
                "image.gif",
                "image.jpeg",
                "image.png",
                "partial_content.txt",
                "patch-content.txt",
                "text-file.txt");

        assertEquals(directoryContents, fileReader.returnDirectoryContents("public/"));
    }

}