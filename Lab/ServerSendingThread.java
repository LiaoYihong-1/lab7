package Lab;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerSendingThread extends Thread{
    private Response response;
    private DatagramSocket socket;
    DatagramPacket packet;
    ServerSendingThread(Response response, DatagramSocket socket, DatagramPacket packet){
        this.response = response;
        this.socket=socket;
        this.packet=packet;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(response);
            byte[] data = byteArrayOutputStream.toByteArray();
            DatagramPacket send = new DatagramPacket(data, data.length, packet.getSocketAddress());
            outputStream.close();
            socket.send(send);
        }catch (IOException I){
            I.printStackTrace();
        }
    }
}
