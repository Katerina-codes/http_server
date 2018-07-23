import java.io.InputStream;
import java.io.OutputStream;

public interface ClientSocket {
    InputStream getInputStream();
    OutputStream getOutputStream();
}
