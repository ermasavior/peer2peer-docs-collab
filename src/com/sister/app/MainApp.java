package com.sister.app;

import com.sister.app.Messenger.Messenger;

import java.io.IOException;

public class MainApp {
    public Controller appController;
    public Messenger appMessenger;
    public int site_id;
    public int counter;

    public MainApp() throws IOException {
        this.appController = new Controller();
        this.appMessenger = new Messenger();
        this.site_id = 0; //Minta messenger
        this.counter = 0;

        this.appMessenger.run();
    }

    public void sendOperation(String type, int pos, char value) {
        Operation op = new Operation(site_id, value, type, pos, counter);
        //TODO: Manggil messenger

        //TODO: Add Controller
        this.appController.addOperation(op);
        counter++;
    }

    public String getCRDTText() {
        return this.appController.CRDTToString();
    }
}
