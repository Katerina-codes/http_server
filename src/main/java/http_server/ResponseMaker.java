package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResponseMaker {

    public String returnFileContents(String resource) {
        byte[] encodedContents;
        String contents;

        if (resource.equals("/")) {
            return "Directory requested. Directory exists but is empty";
        } else {
            try {
                String filePath = String.format("public/%s", resource);
                encodedContents = Files.readAllBytes(Paths.get(filePath));
                contents = new String(encodedContents, "US-ASCII");
            } catch (IOException e) {
                e.printStackTrace();
                contents = "This file does not exist!";
            }
            return contents;
        }
    }

    public String buildWholeResponse(String fileContents, String file, String typeOfRequest) {
        fileContents = handleEmplyFile(fileContents);
        String response = "";
        if (isHeadRequest(fileContents, typeOfRequest)) {
            return returnNoMessageBody(fileContents, file, response);
        } else {
            return returnMessageBody(fileContents, file, response);
        }
    }

    private String returnMessageBody(String fileContents, String file, String response) {
        response = response + statusResponse(file) + "\n\n";
        return response + fileContents;
    }

    private String returnNoMessageBody(String fileContents, String file, String response) {
        response = response + statusResponse(file) + "\n";
        String formatString = String.format("Content-Length: %s\n", fileContents.length());
        return response + formatString + "\n";
    }

    private boolean isHeadRequest(String fileContents, String typeOfRequest) {
        return fileContents.length() == 0 && typeOfRequest.equals("HEAD");
    }

    private String handleEmplyFile(String fileContents) {
        if (fileContents.equals("This file does not exist!") || fileContents.equals("Directory requested. Directory exists but is empty")) {
            fileContents = "";
        }
        return fileContents;
    }

    public String statusResponse(String file) {
        String fileContents = returnFileContents(file);
        if (fileContents.equals("This file does not exist!")) {
            return "HTTP/1.1 404 Not Found";
        } else if (fileContents.equals("Directory requested. Directory exists but is empty") || fileContents.length() > 0) {
            return "HTTP/1.1 200 OK";
        } else {
            return "Unhandled file type";
        }
    }
}