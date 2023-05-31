package client.network;

import common.interfaces.Connection;
import common.interfaces.PackageSeparator;
import common.network.Request;
import common.network.Response;
import common.utility.Serializer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class ClientConnection implements Connection, PackageSeparator {
    private final DatagramChannel client;
    private final InetSocketAddress address;
    private ByteBuffer byteBuffer;
    private final int BUFFER = 2048;
    private final int MAX_BUFFER = BUFFER - 1;
    private final int port;

    public ClientConnection(InetAddress hostAddress, int port) throws IOException {
        this.port = port;
        this.address = new InetSocketAddress(hostAddress, port);
        this.client = DatagramChannel.open();
        client.configureBlocking(false);
        byteBuffer = ByteBuffer.allocate(BUFFER);
    }


    public void sendCommand(Request request) throws IOException {
        byte[] data = Serializer.serialize(request);
        byte[][] splitData = split(data);
        for (byte[] array : splitData) {
            byteBuffer.clear();
            byteBuffer = ByteBuffer.wrap(array);
            client.send(byteBuffer, address);
        }
    }


    public byte[] receive() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER);
        SocketAddress socketAddress = null;
        while (socketAddress == null) {
            socketAddress = client.receive(buffer);
        }
        return buffer.array();
    }

    public Response receiveResult() throws IOException, ClassNotFoundException {
        ArrayList<byte[]> arrayListOfBytes = new ArrayList<>();
        byte[] received;
        do {
            received = receive();
            arrayListOfBytes.add(received);
        } while (received[MAX_BUFFER] != 0);
        byte[] answer = merge(arrayListOfBytes);
        return (Response) Serializer.deserialize(answer);
    }

    @Override
    public void connect(InetAddress addr, int port) throws IOException {
        this.client.connect(new InetSocketAddress(addr, port));
    }

    @Override
    public void disconnect() throws IOException {
        this.client.disconnect();
    }

    public DatagramChannel getClient() {
        return client;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
