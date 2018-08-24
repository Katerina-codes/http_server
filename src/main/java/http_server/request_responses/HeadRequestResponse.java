package http_server.request_responses;

import http_server.RequestHandler;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

import static http_server.ResponseMaker.BLANK_LINE;

public class HeadRequestResponse implements RequestHandler {

    public ResponseMaker responseMaker = new ResponseMaker();

    public ByteArrayOutputStream response(String resourceRequested) {
        ByteArrayOutputStream output = responseMaker.createOutputStream();
        byte[] response = (responseMaker.statusResponse(resourceRequested) + BLANK_LINE).getBytes();
        responseMaker.writeToOutputStream(output, response);
        return output;
    }

}