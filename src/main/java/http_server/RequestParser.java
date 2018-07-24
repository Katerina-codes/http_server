package http_server;

public class RequestParser {

    public String parse(String request) {
        return request.substring(5, request.lastIndexOf(" "));
    }

}
