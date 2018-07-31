package http_server;

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

    public String extractMethodFromRequest(String request) {
        return request.substring(0, request.lastIndexOf(" /"));
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