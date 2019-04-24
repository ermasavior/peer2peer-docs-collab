package com.sister.Messenger;

import com.sister.Operation;
import java.io.IOException;

public class MessengerTest {
    public static void main(String[] args) throws IOException {
        Operation op1 = new Operation(1, 'K', "Insert", 0, 1);
        Messenger.broadcast(op1);
    }
}
