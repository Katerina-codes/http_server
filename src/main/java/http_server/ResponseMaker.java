package http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static http_server.HttpMethods.*;
import static http_server.StatusCodes.METHOD_NOT_ALLOWED;
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

    public byte[] returnResourceContents(String resource) {
        byte[] contents = null;

        if (requestIsToHomePage(resource)) {
            return "".getBytes();
        } else {
            String filePath = String.format("public/%s", resource);
            try {
                contents = Files.readAllBytes(Paths.get(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contents;
        }
    }

    public ByteArrayOutputStream buildWholeResponse(String request) {
        HttpMethods typeOfRequest = requestParser.extractMethodFromRequest(request);
        String resourceRequested = requestParser.parseResource(request);
        List<HttpMethods> methodsRecognised = Arrays.asList(GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE);
        if (methodsRecognised.contains(typeOfRequest)) {
            if (isHeadRequest(typeOfRequest)) {
                return returnNoMessageBody(resourceRequested);
            } else if (typeOfRequest.equals(OPTIONS)) {
                return optionsMessageBody(resourceRequested);
            } else if (typeOfRequest.equals(POST)) {
                return postMessageBody(resourceRequested);
            } else {
                return returnMessageBody(resourceRequested);
            }
        } else {
            return methodNotAllowed();
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

    private ByteArrayOutputStream returnMessageBody(String resourceRequested) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        if (isResourceAvailable(resourceRequested).equals("404")) {
            byte[] statusResponse = (statusResponse(resourceRequested) + "\n").getBytes();
            try {
                output.write(statusResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            byte[] fileContents = returnResourceContents(resourceRequested);
            String contentType = returnContentType(requestParser.parseContentType(resourceRequested));
            byte[] statusResponse = (statusResponse(resourceRequested) + "\n").getBytes();
            byte[] closeConnection = "Connection: close\n".getBytes();
            byte[] formatContentType = String.format("Content-Type: %s\n\n", contentType).getBytes();
            try {
                output.write(statusResponse);
                output.write(closeConnection);
                output.write(formatContentType);
                output.write(fileContents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    private String isResourceAvailable(String resource) {
        if (requestIsToHomePage(resource)) {
            return OK.getStatusCode();
        } else {
            if (!Objects.equals(Arrays.toString(returnResourceContents(resource)), "null")) {
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

    private boolean isHeadRequest(HttpMethods typeOfRequest) {
        return typeOfRequest.equals(HEAD);
    }

    private ByteArrayOutputStream returnNoMessageBody(String resourceRequested) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] response = (statusResponse(resourceRequested) + "\n\n").getBytes();
        try {
            output.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    private ByteArrayOutputStream optionsMessageBody(String resourceRequested) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] response = ("" +
                buildStatusLine(OK) + "\n" +
                "Connection: close\n").getBytes();

        if (resourceRequested.equals("logs")) {
            try {
                outputStream.write(response);
                byte[] logAllows = ("Allow: GET, HEAD, OPTIONS\n").getBytes();
                outputStream.write(logAllows);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputStream;
        } else {
            try {
                outputStream.write(response);
                byte[] textAllows = ("Allow: GET, HEAD, OPTIONS, PUT, DELETE\n").getBytes();
                outputStream.write(textAllows);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputStream;
        }
    }

    private ByteArrayOutputStream postMessageBody(String resourceRequested) {
        ByteArrayOutputStream optionsResponse = optionsMessageBody(resourceRequested);
        if (optionsResponse.toString().contains("POST")) {
            try {
                optionsResponse.write( "POST is not supported".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return optionsResponse;
        } else {
            return methodNotAllowed();
        }
    }

    private ByteArrayOutputStream methodNotAllowed() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = (buildStatusLine(METHOD_NOT_ALLOWED) + "\n" +
                "Connection: close\n" +
                "Allow: GET, HEAD, OPTIONS, PUT, DELETE\n").getBytes();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

}