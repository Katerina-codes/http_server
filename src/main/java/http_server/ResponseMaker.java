package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResponseMaker {

    public String statusResponse() {
        return "HTTP/1.1 200 OK";
    }

    public String returnFileContents(String file) {
        byte[] encoded;
        String contents = null;
        try {
            String filePath = String.format("public/%s", file);
            encoded = Files.readAllBytes(Paths.get(filePath));
            contents = new String(encoded, "US-ASCII");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

}