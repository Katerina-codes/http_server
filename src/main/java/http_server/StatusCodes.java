package http_server;

public enum StatusCodes {

    NO_CONTENT("204", "No Content"),
    NOT_FOUND("404", "Not Found"),
    OK("200", "OK");

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