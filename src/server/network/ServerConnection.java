package server.network;

import common.interfaces.Connection;
import common.interfaces.PackageSeparator;
import common.network.Response;
import common.utility.Serializer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ServerConnection implements Connection, PackageSeparator {
    private final DatagramSocket socket;
    private final int BUFFER = 2048;


    public ServerConnection(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        socket.setReuseAddress(true);
    }


    public Pair<DatagramPacket, byte[]> receiveDataFromClient() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[BUFFER];
        ArrayList<byte[]> arrayListOfReceivedBytes = new ArrayList<>();
        DatagramPacket dpack = new DatagramPacket(buffer, BUFFER - 1);
        socket.receive(dpack);
        arrayListOfReceivedBytes.add(buffer);
        byte[] data = merge(arrayListOfReceivedBytes);
        return new ImmutablePair<>(dpack, data);
    }

    public void sendDataToClient(Response response, SocketAddress addr) throws IOException {
        byte[] toSend = Serializer.serialize(response);
        byte[][] packets = split(toSend);
        for (byte[] toSendBytes : packets) {
            DatagramPacket dpack = new DatagramPacket(toSendBytes, toSendBytes.length, addr);
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

}
