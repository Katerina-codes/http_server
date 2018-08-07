package http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static http_server.Header.*;
import static http_server.HttpMethods.GET;
import static http_server.StatusCodes.*;
import static java.util.Arrays.asList;

public class ResponseMaker {

    public static final String BLANK_LINE = "\n\n";
    public static final String NEW_LINE = "\n";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_GIF = "image/gif";
    public static final String TEXT = "txt";
    public static final String JPEG = "jpeg";
    public static final String PNG = "png";
    private RequestParser requestParser = new RequestParser();
    private FileReader fileReader = new FileReader();

    public String statusResponse(String file) {
        String statusCodeForResource = isResourceAvailable(file);

        List<StatusCodes> statusCodes = asList(StatusCodes.values());
        for (StatusCodes statusCode : statusCodes) {
            if (statusCode.getStatusCode().equals(statusCodeForResource)) {
                return buildStatusLine(statusCode);
            }
        }
        return NOT_FOUND.getStatusMessage();
    }

    public ByteArrayOutputStream buildWholeResponse(String request) {
        HttpMethods typeOfRequest = requestParser.extractMethodFromRequest(request);
        String resourceRequested = requestParser.parseResource(request);
        List<HttpMethods> httpMethods = Arrays.asList(HttpMethods.values());

        if (requestIsToHomePage(resourceRequested) && typeOfRequest.equals(GET)) {
            return returnHomeDirectoryContents(resourceRequested);
        } else if (httpMethods.contains(typeOfRequest)) {
            ByteArrayOutputStream response = new ByteArrayOutputStream();
            for (HttpMethods method : httpMethods) {
                if (method.equals(typeOfRequest)) {
                    response = method.getRequestHandler().response(resourceRequested);
                }
            }
            return response;
        } else {
            return methodNotAllowed();
        }
    }

    public String returnContentType(String fileExtension) {
        switch (fileExtension) {
            case TEXT:
                return TEXT_PLAIN;
            case JPEG:
                return IMAGE_JPEG;
            case PNG:
                return IMAGE_PNG;
            default:
                return IMAGE_GIF;
        }
    }

    public String isResourceAvailable(String resource) {
        if (requestIsToHomePage(resource)) {
            return OK.getStatusCode();
        } else {
            if (fileReader.returnResourceContents(resource) != null) {
                return OK.getStatusCode();
            } else {
                return NOT_FOUND.getStatusCode();
            }
        }
    }

    public String buildStatusLine(StatusCodes statusCode) {
        return HTTP_VERSION.getText() + statusCode.getStatusCode() + " " + statusCode.getStatusMessage();
    }

    public ByteArrayOutputStream methodNotAllowed() {
        ByteArrayOutputStream outputStream = createOutputStream();
        byte[] bytes = (buildStatusLine(METHOD_NOT_ALLOWED) + "\n" +
                CONTENT_LENGTH_ZERO.getText() +
                CLOSE_CONNECTION.getText() +
                METHODS_ALLOWED_FOR_TXT_FILE.getText()).getBytes();
        writeToOutputStream(outputStream, bytes);
        return outputStream;
    }

    public ByteArrayOutputStream writeToOutputStream(ByteArrayOutputStream outputStream, byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    public ByteArrayOutputStream createOutputStream() {
        return new ByteArrayOutputStream();
    }

    private ByteArrayOutputStream returnHomeDirectoryContents(String resourceRequested) {
        byte[] fileContents = (
                "<html><head></head><body>" +
                        "<a href=\"/file1\">" + "file1" + "</a><br>" +
                        "<a href=\"/file2\">" + "file2" + "</a><br>" +
                        "<a href=\"/image.gif\">" + "image.gif" + "</a><br>" +
                        "<a href=\"/image.jpeg\">" + "image.jpeg" + "</a><br>" +
                        "<a href=\"/image.png\">" + "image.png" + "</a><br>" +
                        "<a href=\"/partial_content.txt\">" + "partial_content.txt" + "</a><br>" +
                        "<a href=\"/patch-content.txt\">" + "patch-content.txt" + "</a><br>" +
                        "<a href=\"/text-file.txt\">" + "text-file.txt" + "</a>" +
                        "</body></html>").getBytes();
        byte[] statusResponse = (statusResponse(resourceRequested) + NEW_LINE +
                "Content-Type: text/html" + NEW_LINE + NEW_LINE).getBytes();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeToOutputStream(outputStream, statusResponse);
        writeToOutputStream(outputStream, fileContents);
        return outputStream;
    }

    private boolean requestIsToHomePage(String resource) {
        return resource.equals("/");
    }

}