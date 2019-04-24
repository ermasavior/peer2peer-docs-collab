package com.sister.Messenger;

import com.sister.CRDT.CRDT;
import com.sister.Controller;
import com.sister.Operation;
import com.sister.VersionVector.VersionVector;

import java.io.*;
import java.net.*;

public class Messenger extends Thread {
    public static int MessengerId = 0;

    private Controller controller;

    public Messenger(Controller c){
        this.controller = c;
    }

    public void receiveUDPMessage(String ip, int port) throws	IOException {
        byte[] buffer = new byte[1024];
        MulticastSocket socket = new MulticastSocket(port);
        InetAddress group = InetAddress.getByName(ip);
        socket.setInterface(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
        socket.joinGroup(group);
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            System.out.println(packet.getAddress().getHostName());
            try {
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                Operation messageClass = (Operation) iStream.readObject();
                iStream.close();
                System.out.println(messageClass.toString());
                controller.addOperation(messageClass);
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
            MessengerId++;
            System.out.println("Running");
            System.out.println("Id: " + MessengerId);
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

        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.setInterface(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
        multicastSocket.joinGroup(group);

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(message);
        oo.close();

        byte[] msg = bStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
        multicastSocket.send(packet);
        multicastSocket.leaveGroup(group);
        socket.send(packet);
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        CRDT crdt[] = new CRDT[0];
        VersionVector v[] = new VersionVector[0];
        Operation d[] = new Operation[0];
        Operation o[] = new Operation[0];
        Controller c = new Controller(crdt, d, o, v);
        Messenger messenger1 = new Messenger(c);
        messenger1.start();
        Messenger messenger2 = new Messenger(c);
        messenger2.start();
    }
}
