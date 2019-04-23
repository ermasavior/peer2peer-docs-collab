package Messenger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class UDPTest {
    EchoServer server;
    EchoClient client;

    @Before
    public void setup() throws SocketException, UnknownHostException {
        server = new EchoServer();
        server.start();
        client = new EchoClient();
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
        String echo = client.sendEcho("hello server");
        assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        assertNotEquals("hello server", echo);
    }

    @After
    public void tearDown() throws IOException {
        client.sendEcho("end");
        client.close();
    }

}
