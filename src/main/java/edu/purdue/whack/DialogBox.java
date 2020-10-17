package edu.purdue.whack;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DialogBox {
    @FXML
    private Button exitBtn;

    @FXML
    private void onExitBtn() throws IOException {
        exitBtn.setOnAction(e -> System.out.println("Happy"));
    }
    /*
    try {
       Scene scene = new Scene(App.loadFXML("dialog"));
       stage.setTitle("DialogBox");
       stage.setScene(scene);
       stage.show();
    } catch (IOException ioException) {
       System.out.println("Error Occurred, Check Code");
           }
     */
}
