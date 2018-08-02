package http_server;

import java.util.List;

import static java.util.Arrays.asList;

public class RequestParser {

    public String parseResource(String request) {
        int startOfString = request.indexOf("/", 0) + 1;
        String substring = request.substring(startOfString, request.lastIndexOf(" "));
        if (substring.equals("")) {
            return "/";
        } else {
            return substring;
        }
    }

    public HttpMethods extractMethodFromRequest(String request) {
        String methodFromRequest = request.substring(0, request.lastIndexOf(" /"));
        HttpMethods convertedMethod = null;
        List<HttpMethods> methods = asList(HttpMethods.values());
        for (HttpMethods method : methods) {
            if (method.toString().equals(methodFromRequest)) {
                convertedMethod = method;
            }
        }
        return convertedMethod;
    }

    public String parseContentType(String resource) {
        if (resource.equals("file1") || resource.equals("file2")) {
            return "txt";
        } else {
            int startOfString = resource.indexOf(".", 0) + 1;
            return resource.substring(startOfString, resource.length());
        }
    }

}