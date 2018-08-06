package http_server.request_responses;

import http_server.RequestHandler;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

public class FakeResponse implements RequestHandler {

    public ResponseMaker responseMaker = new  ResponseMaker();

    public ByteArrayOutputStream response(String resourceRequested) {
        return responseMaker.methodNotAllowed();
    }

}