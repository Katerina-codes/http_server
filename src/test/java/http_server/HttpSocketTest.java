package http_server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class HttpSocketTest {

    @Test
    public void readInputFromSocket() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("hello".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpSocket clientSocket = new HttpSocket(new FakeSocket(input, output));

        InputStream inputStream = clientSocket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader lineReader = new BufferedReader(inputStreamReader);

        assertEquals("hello", lineReader.readLine());
    }

    @Test
    public void writeOutputToSocket() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpSocket clientSocket = new HttpSocket(new FakeSocket(input, output));

        output.write("Bye".getBytes());

        assertEquals("Bye", clientSocket.getOutputStream().toString());
    }
}