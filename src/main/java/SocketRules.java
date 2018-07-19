import java.io.InputStream;
import java.io.OutputStream;

public interface SocketRules {
    InputStream getInputStream();
    OutputStream getOutputStream();
}
