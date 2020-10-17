package edu.purdue.whack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController {

    private boolean dragDropSuccess;

    private ArrayList<File> files;

    @FXML
    private Label dragLabel;

    @FXML
    private VBox dragDrop;

    @FXML
    private Button submitBtn;

    @FXML
    private Button logoutBtn;

    public MainController() {
        files = new ArrayList<>();
    }

    @FXML
    public void onSubmitBtn(ActionEvent event) {
        for (File file : files) {
            System.out.println(file.toString());
        }
    }

    public void onDragOver(DragEvent event) {
        if (event.getGestureSource() != dragDrop && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void onDragDropped(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            dragLabel.setText(event.getDragboard().getFiles().toString());
            dragLabel.setFont(new Font("System", 12));
            files.addAll(event.getDragboard().getFiles());
            this.dragDropSuccess = true;
        }
        event.setDropCompleted(this.dragDropSuccess);
        event.consume();
    }

    public void logoutBtn(ActionEvent event) {
    }
}