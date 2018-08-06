package http_server;

import java.io.ByteArrayOutputStream;

public abstract class RequestHandlerClass {

    public abstract ByteArrayOutputStream response(String resourceRequested);

}