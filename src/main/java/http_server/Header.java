package http_server;

public enum Header {
    HTTP_VERSION("HTTP/1.1 "),
    CLOSE_CONNECTION("Connection: close\n"),
    CONTENT_TYPE("Content-Type: "),
    METHODS_ALLOWED_FOR_TXT_FILE("Allow: GET, HEAD, OPTIONS, PUT, DELETE\n"),
    METHODS_ALLOWED_FOR_LOGS("Allow: GET, HEAD, OPTIONS\n");

    private final String content;

    Header(String text) {
        this.content = text;
    }

    public String getText() {
        return content;
    }
}