package http_server.request_responses;

import http_server.RequestHandler;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

import static http_server.Header.*;
import static http_server.ResponseMaker.NEW_LINE;
import static http_server.StatusCodes.OK;

public class OptionsRequestResponse implements RequestHandler {

    public ResponseMaker responseMaker = new ResponseMaker();

    public ByteArrayOutputStream response(String resourceRequested) {
        ByteArrayOutputStream outputStream = responseMaker.createOutputStream();
        byte[] response = (
                "" + responseMaker.buildStatusLine(OK) + NEW_LINE + CLOSE_CONNECTION.getText()
        ).getBytes();

        if (resourceRequested.equals("logs")) {
            responseMaker.writeToOutputStream(outputStream, response);
            responseMaker.writeToOutputStream(outputStream, METHODS_ALLOWED_FOR_LOGS.getText().getBytes());
        } else {
            responseMaker.writeToOutputStream(outputStream, response);
            responseMaker.writeToOutputStream(outputStream, METHODS_ALLOWED_FOR_TXT_FILE.getText().getBytes());
        }
        return outputStream;
    }

}