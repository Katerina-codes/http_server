package http_server;

import java.util.HashMap;

public enum StatusCodes {

    OK("200", "OK"),
    NO_CONTENT("204", "No Content"),
    NOT_FOUND("404", "Not Found");

    private final String statusCode;
    private final String statusMessage;

    StatusCodes(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

}