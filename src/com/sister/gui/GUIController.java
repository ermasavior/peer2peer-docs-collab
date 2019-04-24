package com.sister.gui;

import com.sister.app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {

    private MainApp mainApp;
    private String collaborationCode = "";

    @FXML
    public TextField joinTextField;

    @FXML
    public TextField collabCodeTextField;

    @FXML
    public TextArea editorTextArea;

    @FXML
    public ListView userListView;

    public GUIController() throws IOException {
        this.mainApp = new MainApp();
    }

    @FXML
    public void goToNewDocs(ActionEvent actionEvent) throws IOException {
        this.mainApp = new MainApp();
        System.out.println("Collab"+collaborationCode);
        goToNewPage("EditorWindow.fxml");

        GUIThread guiThread = new GUIThread(this.mainApp, this.editorTextArea);
        guiThread.start();
    }

    @FXML
    public void goToJoinDocs(ActionEvent actionEvent) throws IOException {
        this.mainApp = new MainApp();
        this.collaborationCode = joinTextField.getText();
        System.out.println("Join docs: " + this.collaborationCode);
        goToNewPage("EditorWindow.fxml");

        GUIThread guiThread = new GUIThread(this.mainApp, this.editorTextArea);
        guiThread.start();
    }

    @FXML
    public void addOperation (KeyEvent kEvent) {
        KeyCode keyCode = kEvent.getCode();
        String type;
        char newChar;

        if (keyCode == KeyCode.BACK_SPACE) {
            int idx = this.editorTextArea.getCaretPosition();
            System.out.println(idx-1);
            System.out.println("Backspace pressed");
            type = "Delete";
            newChar = '0';
            this.mainApp.sendOperation(type, idx-1, newChar);
            System.out.println(this.mainApp.getCRDTText());

        } else if (!(keyCode.isFunctionKey() || keyCode.isArrowKey() || keyCode.isMediaKey() || keyCode.isModifierKey())) {
            type = "Insert";
            int idx = this.editorTextArea.getCaretPosition();
            newChar = kEvent.getText().charAt(0);
            System.out.println(idx);
            System.out.println(newChar);
            if (this.mainApp != null) {
                System.out.println("g null");
            }
            this.mainApp.sendOperation(type, idx, newChar);
            System.out.println(this.mainApp.getCRDTText());
        }
    }

    private void goToNewPage(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class GUIThread extends Thread {

    private MainApp mainApp;
    public TextArea editorTextArea;

    public GUIThread(MainApp mainApp, TextArea editorTextArea) {
        this.mainApp = mainApp;
        this.editorTextArea = editorTextArea;
    }

    @Override
    public void run() {
        while(true) {
            String CRDTText = this.mainApp.getCRDTText();
//            System.out.println("CEKKKK "+CRDTText);
            if (this.editorTextArea != null) {
//                this.editorTextArea.setText(CRDTText);
            }
        }
    }
}