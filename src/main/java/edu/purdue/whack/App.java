package edu.purdue.whack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.purdue.whack.auth.AuthModule;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public final static Injector injector = Guice.createInjector(new AuthModule());

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("welcome"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("EZPrint");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}