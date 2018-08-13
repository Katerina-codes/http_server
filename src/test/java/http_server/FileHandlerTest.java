package http_server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FileHandlerTest {

    private FileHandler fileHandler;
    private String directoryPath;
    private final String dummyFilePath = "dummyFile.txt";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        fileHandler = new FileHandler();
        directoryPath = temporaryFolder.getRoot().getPath();
    }

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
        fileHandler.createFile(directoryPath, dummyFilePath);

        assertEquals(1, fileHandler.returnDirectoryContents(directoryPath).size());
    }

    @Test
    public void deleteFile() throws DirectoryNotFoundException {
        fileHandler.createFile(directoryPath, dummyFilePath);
        fileHandler.deleteFile(directoryPath, dummyFilePath);

        assertEquals(0, fileHandler.returnDirectoryContents(directoryPath).size());
    }

    @Test
    public void returnsEmptyByteArrayInsteadOfNull() {

        Assert.assertArrayEquals(new byte[0], fileHandler.returnResourceContents("fake_file.txt"));
    }

}