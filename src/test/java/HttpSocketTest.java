import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class HttpSocketTest {

    @Test
    public void readInputFromSocket() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("hello".getBytes());
        HttpSocket clientSocket = new HttpSocket(new FakeSocket(input));

        InputStream inputStream = clientSocket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader lineReader = new BufferedReader(inputStreamReader);

        assertEquals("hello", lineReader.readLine());
    }
}
