import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class MulticastSender {

    public void sendMessage(String message, String groupName, int port) {
        byte[] buffer = message.getBytes();
        try {
            InetAddress group = InetAddress.getByName(groupName);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
            socket.close();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}
