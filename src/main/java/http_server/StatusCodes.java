package http_server;

public enum StatusCodes {

    DIRECTORY_WITH_NO_CONTENT("204", "No Content"),
    FILE_NOT_FOUND("404", "Not Found"),
    REQUEST_SUCCEEDED("200", "OK");

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