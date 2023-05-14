package server.network;

import common.interfaces.Connection;
import common.interfaces.PackageSeparator;
import common.network.Request;
import common.network.Response;
import common.utility.Serializer;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ServerConnection implements Connection, PackageSeparator {
    private final DatagramSocket socket;
    private DatagramPacket dpack;
    private final int BUFFER = 2048;
    private final byte[] buffer;


    public ServerConnection(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        socket.setReuseAddress(true);
        this.buffer = new byte[BUFFER];
    }

    public Request receiveDataFromClient() throws IOException, ClassNotFoundException {
        ArrayList<byte[]> arrayListOfReceivedBytes = new ArrayList<>();
        dpack = new DatagramPacket(buffer, BUFFER - 1);
        socket.receive(dpack);
        connect(dpack.getAddress(), dpack.getPort());
        arrayListOfReceivedBytes.add(buffer);
        byte[] data = merge(arrayListOfReceivedBytes);
        return (Request) Serializer.deserialize(data);
    }

    public void sendDataToClient(Response response) throws IOException {
        byte[] toSend = Serializer.serialize(response);
        byte[][] packets = split(toSend);
        for (byte[] toSendBytes : packets) {
            dpack = new DatagramPacket(toSendBytes, toSendBytes.length, dpack.getAddress(), dpack.getPort());
            socket.send(dpack);
        }

    }


    @Override
    public void connect(InetAddress addr, int port) throws IOException {
        socket.connect(new InetSocketAddress(addr, port));

    }

    @Override
    public void disconnect() throws IOException {
        socket.disconnect();
    }

    public DatagramPacket getDpack() {
        return dpack;
    }
}
