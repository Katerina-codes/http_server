package http_server;

import http_server.request_responses.*;

public enum HttpMethods {
    GET(new GetRequestResponse()),
    HEAD(new HeadRequestResponse()),
    POST(new PostRequestResponse()),
    PUT(new PutRequestResponse()),
    DELETE(new FakeResponse()),
    CONNECT(new FakeResponse()),
    OPTIONS(new OptionsRequestResponse()),
    TRACE(new FakeResponse());

    private final RequestHandler requestHandler;

    HttpMethods(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }
}