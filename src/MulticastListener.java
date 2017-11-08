import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class MulticastListener extends Thread{
    private byte[] buffer = new byte[256];
    private String avatar;
    private volatile String groupName;
    private volatile int portNum;
    private volatile ArrayList<String> messages;
    private volatile Boolean shutdown;
    private volatile int numOfMessages;

    public MulticastListener(String groupID, int portID, String avatar, int messNum, ArrayList<String> mess) {
        numOfMessages = messNum;
        this.avatar = avatar;
        this.groupName = groupID;
        this.portNum = portID;
        shutdown = false;
        messages = mess;
    }

    public void run() {

        try {
            InetAddress group = InetAddress.getByName(groupName);
            MulticastSocket socket = new MulticastSocket(portNum);
            socket.joinGroup(group);
            messages.add(avatar + " is connected to chat at  " + groupName);
            numOfMessages++;
            while (!shutdown) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                if(!message.contains(avatar)){
                    messages.add(message);
                    numOfMessages++;
                }
            }
            socket.leaveGroup(group);
            socket.close();
            System.out.println("Socket has been stopped safely");
        }


        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized ArrayList<String> getMessages(){
        return messages;
    }
    public synchronized void beginShutdownSequence() {
        shutdown = true;
    }
    public synchronized void addMessage(String message) {
        messages.add(message);
        numOfMessages++;
    }
    public synchronized int amountOfMessages() {
        return numOfMessages;
    }
}
