package com.sister.app.Messenger;

import com.sister.app.Controller;
import com.sister.app.Operation;

import java.io.*;
import java.net.*;

public class Messenger extends Thread {

    public void receiveUDPMessage(String ip, int port) throws	IOException {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(port);
        InetAddress group = InetAddress.getByName(ip);
        socket.joinGroup(group);
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            try {
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                Operation messageClass = (Operation) iStream.readObject();
                iStream.close();
                System.out.println(messageClass.toString());
                //TODO: Call Controller
//                if (!messageClass.getId().equals(com.sister.Controller.id)) {
//                    if (messageClass.getOperation() == 'i') {
//                        insertRemote(messageClass);
//                    } else if (messageClass.getOperation() == 'd') {
//                        deleteRemote(messageClass);
//                    }
//                }
            } catch (Exception e) {
                System.out.println("error 1");
                e.printStackTrace();
                System.out.println(e.toString());
                break;
            }
        }
        socket.leaveGroup(group);
        socket.close();
    }

    @Override
    public void run() {
        try {
            System.out.println("Running");
            receiveUDPMessage("230.0.0.0", 4444);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void broadcast(Operation message) throws IOException {
        System.out.println("Broadcasting Message");
        sendUDPMessage(message, "230.0.0.0", 4444);
    }

    private static void sendUDPMessage(Operation message, String ipAddress, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group =InetAddress.getByName(ipAddress);

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(message);
        oo.close();

        byte[] msg = bStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
        socket.send(packet);
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Messenger messenger1 = new Messenger();
        messenger1.run();
    }
}
