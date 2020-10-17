package edu.purdue.whack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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


    public MainController() {
        files = new ArrayList<>();
    }
    @FXML
    public void onSubmitBtn() {
        submitBtn.setOnAction(event -> {
            for (File file : files) {
                System.out.println(file.toString());
            }
        });
    }

    public void setOnDragOver() {
        dragDrop.setOnDragOver(event -> {
            if (event.getGestureSource() != dragDrop && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
    }

    public void setOnDragDropped() {
        AtomicBoolean success = new AtomicBoolean(false);
        dragDrop.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                dragLabel.setText(event.getDragboard().getFiles().toString());
                dragLabel.setFont(new Font("System", 12));
                files.addAll(event.getDragboard().getFiles());
                success.set(true);
            }
            event.setDropCompleted(success.get());
            event.consume();
        });
        dragDropSuccess = success.get();
    }
}