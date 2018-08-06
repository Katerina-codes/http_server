package http_server.request_responses;

import http_server.RequestHandler;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

import static http_server.HttpMethods.POST;

public class PostRequestResponse implements RequestHandler {

    public ResponseMaker responseMaker = new  ResponseMaker();

    public ByteArrayOutputStream response(String resourceRequested) {
        ByteArrayOutputStream optionsResponse = new OptionsRequestResponse().response(resourceRequested);
        if (optionsResponse.toString().contains(POST.toString())) {
            responseMaker.writeToOutputStream(optionsResponse, "POST is not supported".getBytes());
            return optionsResponse;
        } else {
            return responseMaker.methodNotAllowed();
        }
    }

}