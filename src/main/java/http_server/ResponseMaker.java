package http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static http_server.Header.*;
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

        if (httpMethods.contains(typeOfRequest)) {
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
        if (fileReader.requestIsToHomePage(resource)) {
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

    public ByteArrayOutputStream optionsMessageBody(String resourceRequested) {
        ByteArrayOutputStream outputStream = createOutputStream();
        byte[] response = (
                "" + buildStatusLine(OK) + NEW_LINE + CLOSE_CONNECTION.getText()
        ).getBytes();

        if (resourceRequested.equals("logs")) {
            writeToOutputStream(outputStream, response);
            writeToOutputStream(outputStream, METHODS_ALLOWED_FOR_LOGS.getText().getBytes());
        } else {
            writeToOutputStream(outputStream, response);
            writeToOutputStream(outputStream, METHODS_ALLOWED_FOR_TXT_FILE.getText().getBytes());
        }
        return outputStream;
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

}