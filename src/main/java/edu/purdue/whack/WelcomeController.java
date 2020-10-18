package edu.purdue.whack;

import edu.purdue.whack.auth.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button loginBtn;
    @FXML
    private Label waitingLabel;
    private final AuthService authService;

    @Inject
    public WelcomeController(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    public void onLoginBtn(ActionEvent event) {
        waitingLabel.setVisible(true);
        this.authService.getAccessToken();
        waitingLabel.setVisible(false);

        try {
            App.setRoot("main");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
