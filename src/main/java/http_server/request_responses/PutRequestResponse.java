package http_server.request_responses;

import http_server.DirectoryNotFoundException;
import http_server.FileHandler;
import http_server.RequestHandler;
import http_server.ResponseMaker;

import java.io.ByteArrayOutputStream;

import static http_server.StatusCodes.CREATED;

public class PutRequestResponse implements RequestHandler {

    public ByteArrayOutputStream response(String resourceRequested) {
        ResponseMaker responseMaker = new ResponseMaker();
        ByteArrayOutputStream output = responseMaker.createOutputStream();
        FileHandler fileHandler = new FileHandler();
        byte[] response = new byte[0];

        try {
            String path = "public/";
            if (!fileHandler.returnDirectoryContents(path).contains(resourceRequested)) {
                fileHandler.createFile(path, resourceRequested);
                response = responseMaker.buildStatusLine(CREATED).getBytes();
            }
        } catch (DirectoryNotFoundException e) {
            e.printStackTrace();
        }
        return responseMaker.writeToOutputStream(output, response);
    }

}