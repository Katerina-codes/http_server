package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public byte[] returnResourceContents(String resource) {
        byte[] contents = null;
        String filePath = String.format("public/%s", resource);
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                contents = Files.readAllBytes(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

}