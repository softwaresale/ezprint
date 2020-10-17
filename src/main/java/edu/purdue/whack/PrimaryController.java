package edu.purdue.whack;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML
    private Button loginBtn;

    @FXML
    private void onLoginBtn() throws IOException {
        //loginBtn.setOnAction(e -> Class.AuthService)
        System.out.println("Goofy");
    }
}
