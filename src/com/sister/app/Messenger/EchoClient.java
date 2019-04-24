package com.sister.Messenger;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public EchoClient() throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String message) throws IOException {
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close(){
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();
        String message = "";
        while (!message.equals("end")){
            Scanner in = new Scanner(System.in);
            message = in.nextLine();
            System.out.println(message);
            String response = client.sendEcho(message);
            System.out.println(response);
        }
    }
}
