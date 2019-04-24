package com.sister.app;

import com.sister.app.Messenger.Messenger;

import java.io.IOException;
import java.util.Random;

public class MainApp {
    public Controller appController;
    public Messenger appMessenger;
    public int site_id;
    public int counter;

    public MainApp() throws IOException {
        this.appController = new Controller();
        this.appMessenger = new Messenger(this.appController);
        this.site_id = (new Random()).nextInt(50); //Minta messenger
        this.counter = 0;

        this.appMessenger.start();
    }

    public void sendOperation(String type, int pos, char value) throws IOException {
        Operation op = new Operation(site_id, value, type, pos, counter);
        //TODO: Manggil messenger
        Messenger.broadcast(op);
        //TODO: Add Controller
        this.appController.apply(op);
        counter++;
    }

    public String getCRDTText() {
        return this.appController.CRDTToString();
    }
}
