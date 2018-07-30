package http_server;

public enum StatusCodes {

    DIRECTORY_WITH_NO_CONTENT(204, "Resource exists but is empty"),
    FILE_NOT_FOUND(404, "This file has not been found"),
    REQUEST_HAS_SUCCEEDED(200, "HTTP/1.1 200 OK");

    private final int statusCode;
    private final String message;

    StatusCodes(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}