package Messenger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class EchoServer extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public EchoServer() throws SocketException {
        socket = new DatagramSocket(4445);
    }

    @Override
    public void run() {
        super.run();
        running = true;
        while (running){
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            //RECEIVE PACKET
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            System.out.println("packet length: " + packet.getLength());
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("From Client [" + packet.getAddress() + ":" + packet.getPort() + "] : " + received);
            if(received.equals("end")){
                running = false;
                continue;
            }
            //SEND PACKET
            packet = new DatagramPacket(buf, buf.length, address, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws SocketException {
        EchoServer server = new EchoServer();
        server.start();
    }
}
