package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUIController {

    private String collaborationCode = "";

    @FXML
    public TextField joinTextField;

    @FXML
    public TextField collabCodeTextField;

    @FXML
    public TextArea editorTextArea;

    @FXML
    public ListView userListView;

    @FXML
    public void goToNewDocs(ActionEvent actionEvent) {
        System.out.println("Collab"+collaborationCode);
        goToNewPage("EditorWindow.fxml");
    }

    @FXML
    public void goToJoinDocs(ActionEvent actionEvent) {
        this.collaborationCode = joinTextField.getText();
        System.out.println("Join docs: " + this.collaborationCode);
        goToNewPage("EditorWindow.fxml");
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
