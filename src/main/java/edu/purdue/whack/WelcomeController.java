package edu.purdue.whack;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private Button loginBtn;

    @FXML
    private void onLoginBtn() throws IOException {
        Stage stage = new Stage();
       loginBtn.setOnAction(e -> {
           try {
               Scene scene = new Scene(App.loadFXML("dialog"));
               stage.setTitle("DialogBox");
               stage.setScene(scene);
               stage.show();
           } catch (IOException ioException) {
               System.out.println("Error Occurred, Check Code");
           }

       });
    }
}
