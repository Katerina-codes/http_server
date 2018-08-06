package http_server.request_responses;

import http_server.FileReader;
import http_server.RequestHandler;
import http_server.RequestParser;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

import static http_server.Header.CLOSE_CONNECTION;
import static http_server.StatusCodes.NOT_FOUND;

public class GetRequestResponse implements RequestHandler {

    public ResponseMaker responseMaker = new  ResponseMaker();
    private FileReader fileReader = new FileReader();
    private RequestParser requestParser = new RequestParser();

    public ByteArrayOutputStream response(String resourceRequested) {
        ByteArrayOutputStream output = responseMaker.createOutputStream();
        if (responseMaker.isResourceAvailable(resourceRequested).equals(NOT_FOUND.getStatusCode())) {
            byte[] statusResponse = (responseMaker.statusResponse(resourceRequested) + "\n").getBytes();
            output = responseMaker.writeToOutputStream(output, statusResponse);
        } else {
            byte[] fileContents = fileReader.returnResourceContents(resourceRequested);
            String contentType = responseMaker.returnContentType(requestParser.parseContentType(resourceRequested));
            byte[] statusResponse = (responseMaker.statusResponse(resourceRequested) + "\n").getBytes();
            byte[] closeConnection = CLOSE_CONNECTION.getText().getBytes();
            byte[] formatContentType = String.format("Content-Type: %s\n\n", contentType).getBytes();
            responseMaker.writeToOutputStream(output, statusResponse);
            responseMaker.writeToOutputStream(output, closeConnection);
            responseMaker.writeToOutputStream(output, formatContentType);
            responseMaker.writeToOutputStream(output, fileContents);
        }
        return output;
    }

}