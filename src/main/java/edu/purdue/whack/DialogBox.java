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
}
