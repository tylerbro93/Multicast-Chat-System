import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSender {
    private DatagramSocket socket;
    private InetAddress group;
    private DatagramPacket packet;
    private byte[] buffer;

    public void sendMessage(String message, String groupName, int port) {
        buffer = message.getBytes();
        try {
            group = InetAddress.getByName(groupName);
            socket = new DatagramSocket();
            packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
            socket.close();
        }
        catch (Exception err) {
            System.out.println(err);
        }
    }
}
