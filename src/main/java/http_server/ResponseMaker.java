package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static http_server.StatusCodes.NO_CONTENT;
import static http_server.StatusCodes.NOT_FOUND;
import static http_server.StatusCodes.OK;

public class ResponseMaker {

    public String statusResponse(String file) {
        String statusCode = checkIfResourceIsAvailable(file);

        if (statusCode.equals(OK.getStatusCode())) {
            return buildStatusLine(OK);
        } else if (statusCode.equals(NOT_FOUND.getStatusCode())) {
            return buildStatusLine(NOT_FOUND);
        } else if (statusCode.equals(NO_CONTENT.getStatusCode())) {
            return buildStatusLine(NO_CONTENT);
        } else {
            return "Unhandled file type";
        }
    }

    public String checkIfResourceIsAvailable(String resource) {
        if (requestIsToHomePage(resource)) {
            return OK.getStatusCode();
        } else {
            if (returnResourceContents(resource) != null) {
                return OK.getStatusCode();
            } else {
                return NOT_FOUND.getStatusCode();
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

    private String buildStatusLine(StatusCodes statusCode) {
        return "HTTP/1.1 " + statusCode.getStatusCode() + " " + statusCode.getStatusMessage();
    }

}