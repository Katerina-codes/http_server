package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static http_server.StatusCodes.DIRECTORY_WITH_NO_CONTENT;
import static http_server.StatusCodes.FILE_NOT_FOUND;
import static http_server.StatusCodes.REQUEST_SUCCEEDED;

public class ResponseMaker {

    public String checkIfResourceIsAvailable(String resource) {
        if (requestIsToHomePage(resource)) {
            return REQUEST_SUCCEEDED.getStatusCode();
        } else {
            if (returnResourceContents(resource) != null) {
                return REQUEST_SUCCEEDED.getStatusCode();
            } else {
                return FILE_NOT_FOUND.getStatusCode();
            }
        }
    }

    private boolean requestIsToHomePage(String resource) {
        return resource.equals("/");
    }

    public String returnResourceContents(String resource) {
        byte[] encodedContents;
        String contents = null;

        if (requestIsToHomePage(resource)) {
            return "";
        } else {
            String filePath = String.format("public/%s", resource);
            try {
                encodedContents = Files.readAllBytes(Paths.get(filePath));
                contents = new String(encodedContents, "US-ASCII");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contents;
        }
    }

    public String buildWholeResponse(String resourceContents, String resource, String typeOfRequest) {
        String response = "";
        if (isHeadRequest(typeOfRequest)) {
            return returnNoMessageBody(resourceContents, resource, response);
        } else {
            return returnMessageBody(resourceContents, resource, response);
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

    private boolean isHeadRequest(String typeOfRequest) {
        return typeOfRequest.equals("HEAD");
    }

    public String statusResponse(String file) {
        String fileContents = checkIfResourceIsAvailable(file);
        if (fileContents.equals(REQUEST_SUCCEEDED.getStatusCode())) {
            return buildStatusLine(REQUEST_SUCCEEDED);
        } else if (fileContents.equals(FILE_NOT_FOUND.getStatusCode())) {
            return buildStatusLine(FILE_NOT_FOUND);
        } else if (fileContents.equals(DIRECTORY_WITH_NO_CONTENT.getStatusCode())) {
            return buildStatusLine(DIRECTORY_WITH_NO_CONTENT);
        } else {
            return "Unhandled file type";
        }
    }

    private String buildStatusLine(StatusCodes statusCode) {
        return "HTTP/1.1 " + statusCode.getStatusCode() + " " + statusCode.getStatusMessage();
    }

}