import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;

public class MulticastListener extends Thread{
    public MulticastSocket socket;
    public byte[] buffer = new byte[256];
    public InetAddress group;
    public DatagramPacket packet;
    private String avatar;
    volatile String groupName;
    volatile int portNum;
    volatile ArrayList<String> messages;
    volatile Boolean shutdown;
    volatile int numOfMessages;

    public MulticastListener(String groupID, int portID, String avatar, int messNum, ArrayList<String> mess) {
        numOfMessages = messNum;
        this.avatar = avatar;
        this.groupName = groupID;
        this.portNum = portID;
        shutdown = true;
        messages = mess;
    }

    public void run() {

        try {
            group = InetAddress.getByName(groupName);
            socket = new MulticastSocket(portNum);
            socket.joinGroup(group);
            messages.add(avatar + " is connected to chat at  " + groupName);
            numOfMessages++;
            while (shutdown == false) {
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                if(message.contains(avatar) != true){
                    messages.add(message);
                    numOfMessages++;
                }
            }
            socket.leaveGroup(group);
            socket.close();
            System.out.println("Socket has been stopped safely");
        }


        catch (IOException e) {
            System.out.println(e);
        }
    }

    public synchronized void updateData(String g, int p) {
        this.portNum = p;
        this.groupName = g;
        shutdown = false;
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
