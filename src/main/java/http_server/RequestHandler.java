package http_server;

import java.io.ByteArrayOutputStream;

public interface RequestHandler {

    public ByteArrayOutputStream response(String resourceRequested);

}