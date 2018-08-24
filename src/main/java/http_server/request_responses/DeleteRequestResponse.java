package http_server.request_responses;

import http_server.FileHandler;
import http_server.RequestHandler;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

import static http_server.StatusCodes.OK;

public class DeleteRequestResponse implements RequestHandler {

    private FileHandler fileHandler = new FileHandler();
    private ResponseMaker responseMaker = new ResponseMaker();

    public ByteArrayOutputStream response(String resourceRequested) {
        ByteArrayOutputStream output = responseMaker.createOutputStream();

        fileHandler.deleteFile("public/", resourceRequested);
        String response = responseMaker.buildStatusLine(OK);
        responseMaker.writeToOutputStream(output, response.getBytes());
        return output;
    }
}
