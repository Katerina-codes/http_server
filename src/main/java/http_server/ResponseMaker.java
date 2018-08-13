package http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static http_server.Header.*;
import static http_server.HttpMethods.GET;
import static http_server.StatusCodes.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

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
    public static final String TEXT_HTML = "text/html";
    public static final String SPACE = " ";
    private RequestParser requestParser = new RequestParser();
    private FileHandler fileHandler = new FileHandler();

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
            return respondToRequest(typeOfRequest, resourceRequested, httpMethods);
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
            if (fileHandler.returnResourceContents(resource).length != 0) {
                return OK.getStatusCode();
            } else {
                return NOT_FOUND.getStatusCode();
            }
        }
    }

    public String buildStatusLine(StatusCodes statusCode) {
        return HTTP_VERSION.getText() + statusCode.getStatusCode() + SPACE + statusCode.getStatusMessage();
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
        List<String> files = emptyList();
        files = getDirectoryContents(files);
        String directoryContents = getFileNames(files);
        byte[] statusResponse = buildDirectoryResponse(resourceRequested);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeToOutputStream(outputStream, statusResponse);
        writeToOutputStream(outputStream, directoryContents.getBytes());
        return outputStream;
    }

    private byte[] buildDirectoryResponse(String resourceRequested) {
        return (statusResponse(resourceRequested) + NEW_LINE +
                CONTENT_TYPE.getText() + TEXT_HTML + NEW_LINE + NEW_LINE).getBytes();
    }

    private List<String> getDirectoryContents(List<String> files) {
        try {
            files = fileHandler.returnDirectoryContents("public/");
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
        }
        return files;
    }

    private ByteArrayOutputStream respondToRequest(HttpMethods typeOfRequest, String resourceRequested, List<HttpMethods> httpMethods) {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        for (HttpMethods method : httpMethods) {
            if (method.equals(typeOfRequest)) {
                response = method.getRequestHandler().response(resourceRequested);
            }
        }
        return response;
    }

    private String getFileNames(List<String> files) {
        String directoryContents = "<html><head></head><body>";
        for (String file : files) {
            directoryContents = directoryContents.concat(buildLink(file));
        }
        directoryContents = directoryContents.concat("</body></html>");
        return directoryContents;
    }

    private String buildLink(String filePath) {
        return "<a href=\"/" + filePath + "\">" + filePath + "</a><br>";
    }

    private boolean requestIsToHomePage(String resource) {
        return resource.equals("/");
    }

}