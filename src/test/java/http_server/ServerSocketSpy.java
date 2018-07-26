package http_server;

import java.io.*;

public class ServerSocketSpy implements ServerSocketManager {

    public ClientSocket clientSocketSpy;
    boolean acceptWasCalled = false;

    public ServerSocketSpy(ClientSocket clientSocketSpy) {
        this.clientSocketSpy = clientSocketSpy;
    }

    public ClientSocket accept() {
        acceptWasCalled = true;
        return clientSocketSpy;
    }

    public static class ClientSocketSpy implements ClientSocket {

        private InputStream inputStream;
        private ByteArrayOutputStream outputStream;
        public boolean getOutputStreamWasCalled = false;
        public boolean getInputStreamWasCalled = false;

        public ClientSocketSpy(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        public InputStream getInputStream() {
            getInputStreamWasCalled = true;
            return inputStream;
        }

        public OutputStream getOutputStream() {
            getOutputStreamWasCalled = true;
            return outputStream;
        }

        public void close() {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getOutputStreamContents() {
            return outputStream.toString();
        }


    }
}
