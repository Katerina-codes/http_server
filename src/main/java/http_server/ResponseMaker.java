package http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static http_server.StatusCodes.NOT_FOUND;
import static http_server.StatusCodes.OK;
import static java.util.Arrays.asList;

public class ResponseMaker {

    private RequestParser requestParser = new RequestParser();

    public String statusResponse(String file) {
        String statusCodeForResource = isResourceAvailable(file);

        List<StatusCodes> statusCodes = asList(StatusCodes.values());
        for (StatusCodes statusCode : statusCodes) {
            if (statusCode.getStatusCode().equals(statusCodeForResource)) {
                return buildStatusLine(statusCode);
            }
        }
        return "Unhandled file type";
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

    public String buildWholeResponse(String request) {
        String typeOfRequest = requestParser.extractMethodFromRequest(request);
        String resourceRequested = requestParser.parseResource(request);
        if (isHeadRequest(typeOfRequest)) {
            return returnNoMessageBody(resourceRequested);
        } else if (typeOfRequest.equals("OPTIONS")) {
            return optionsMessageBody(resourceRequested);
        } else {
            return returnMessageBody(resourceRequested);
        }
    }

    public String returnContentType(String fileExtension) {
        switch (fileExtension) {
            case "txt":
                return "text/plain";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "image/gif";
        }
    }

    private String returnMessageBody(String resourceRequested) {
        String fileContents = returnResourceContents(resourceRequested);
        String contentType = returnContentType(requestParser.parseContentType(resourceRequested));
        String response = "" + statusResponse(resourceRequested) + "\n" +
                "Connection: close\n" +
                String.format("Content-Type: %s\n\n", contentType);
        return response + fileContents;
    }

    private String isResourceAvailable(String resource) {
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

    private String buildStatusLine(StatusCodes statusCode) {
        return "HTTP/1.1 " + statusCode.getStatusCode() + " " + statusCode.getStatusMessage();
    }

    private boolean requestIsToHomePage(String resource) {
        return resource.equals("/");
    }

    private boolean isHeadRequest(String typeOfRequest) {
        return typeOfRequest.equals("HEAD");
    }

    private String returnNoMessageBody(String resourceRequested) {
        String fileContents = "";
        String response = fileContents + statusResponse(resourceRequested) + "\n";
        return response + "\n";
    }

    private String optionsMessageBody(String resourceRequested) {
        String response = "" +
                buildStatusLine(OK) + "\n" +
                "Connection: close\n";

        if (resourceRequested.equals("logs")) {
            return response + "Allow: GET, HEAD, OPTIONS\n";
        } else {
            return response + "Allow: GET, HEAD, OPTIONS, PUT, DELETE\n";
        }
    }

}