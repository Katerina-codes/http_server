package http_server;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class FileHandlerTest {

    @Test
    public void returnsContentsOfFileOne() {
        FileHandler fileHandler = new FileHandler();

        Assert.assertArrayEquals("file1 contents".getBytes(), fileHandler.returnResourceContents("file1"));
    }

    @Test
    public void returnAllFiles() throws DirectoryNotFoundException {
        FileHandler fileHandler = new FileHandler();

        List<String> directoryContents = asList("file1",
                "file2",
                "image.gif",
                "image.jpeg",
                "image.png",
                "partial_content.txt",
                "patch-content.txt",
                "text-file.txt");

        assertEquals(directoryContents, fileHandler.returnDirectoryContents("public/"));
    }

    @Test
    public void createsNewFile() throws DirectoryNotFoundException {
        FileHandler fileHandler = new FileHandler();
        String directoryPath = "src/test/java/http_server/DummyDirectory/";

        fileHandler.createFile(directoryPath, "dummyFile.txt");

        assertTrue(fileHandler.returnDirectoryContents(directoryPath).size() == 1);
    }

    @Test
    public void deleteFile() throws DirectoryNotFoundException {
        FileHandler fileHandler = new FileHandler();
        String directoryPath = "src/test/java/http_server/DummyDirectory/";

        fileHandler.createFile(directoryPath, "dummyFile.txt");
        fileHandler.deleteFile(directoryPath, "dummyFile.txt");

        assertTrue(fileHandler.returnDirectoryContents(directoryPath).size() == 0);
    }

}