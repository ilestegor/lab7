package common.interfaces;

import java.io.IOException;
import java.net.InetAddress;

public interface Connection {
    void connect(InetAddress addr, int port) throws IOException;

    void disconnect() throws IOException;

}
