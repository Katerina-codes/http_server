package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResponseMaker {

    public String returnFileContents(String file) {
        byte[] encodedContents;
        String contents = null;
        try {
            String filePath = String.format("public/%s", file);
            encodedContents = Files.readAllBytes(Paths.get(filePath));
            contents = new String(encodedContents, "US-ASCII");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    public String buildWholeResponse(String fileContents) {
        String response = "";

        response = response + statusResponse() + "\n\n";
        response = response + fileContents;
        return response;
    }

    public String statusResponse() {
        return "HTTP/1.1 200 OK";
    }

}