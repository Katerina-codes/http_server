package http_server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {

    public String parseRequest(BufferedReader lineReader) {
        String requestContent = "";
        try {
            requestContent = lineReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestContent;
    }

    public String parse(String request) {
        return request.substring(5, request.lastIndexOf(" "));
    }

}
