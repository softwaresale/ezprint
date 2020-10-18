package edu.purdue.whack;

import edu.purdue.whack.email.EmailService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController {

    private boolean dragDropSuccess;
    private EmailService emailService;
    private ArrayList<File> files;

    @FXML
    private Label dragLabel;

    @FXML
    private VBox dragDrop;

    @Inject
    public MainController(EmailService service) {
        files = new ArrayList<>();
        this.emailService = service;
    }

    @FXML
    public void onSubmitBtn(ActionEvent event) throws IOException {
        // TODO show processing indicator
        this.emailService.sendPrintRequest(this.files);
        // TODO stop processing indicator
        this.files.clear(); // Clear files from list
        // TODO Show confirmation message
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