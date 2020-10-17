package edu.purdue.whack;

import edu.purdue.whack.auth.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class WelcomeController {

    @FXML
    private Button loginBtn;

    @FXML
    private Label waitingLabel;

    @FXML
    private void onLoginBtn() throws IOException {

       loginBtn.setOnAction(e -> {
           waitingLabel.setVisible(true);
           try {
               new AuthService().getAccessToken();
           } catch (ExecutionException | InterruptedException | MalformedURLException | URISyntaxException executionException) {
               executionException.printStackTrace();
           }
           waitingLabel.setVisible(false);

           try {
               App.setRoot("main");
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }
       });
    }
}
