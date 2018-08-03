package http_server;

import java.util.List;

import static http_server.ResponseMaker.TEXT;
import static java.util.Arrays.asList;

public class RequestParser {

    public static final int FROM_STARTING_INDEX = 0;

    public String parseResource(String request) {
        String rootDirectory = "/";
        int startOfString = getStartOfString(request, rootDirectory, FROM_STARTING_INDEX);
        String substring = getSubstring(request, startOfString, " ");
        if (substring.equals("")) {
            return rootDirectory;
        } else {
            return substring;
        }
    }

    public HttpMethods extractMethodFromRequest(String request) {
        String methodFromRequest = getSubstring(request, FROM_STARTING_INDEX, " /");
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
            return TEXT;
        } else {
            int startOfString = getStartOfString(resource, ".", FROM_STARTING_INDEX);
            return resource.substring(startOfString, resource.length());
        }
    }

    private int getStartOfString(String resource, String fromCharacter, int fromIndex) {
        int addOneToIndex = 1;
        return resource.indexOf(fromCharacter, fromIndex) + addOneToIndex;
    }

    private String getSubstring(String request, int startOfString, String endIndex) {
        return request.substring(startOfString, request.lastIndexOf(endIndex));
    }

}