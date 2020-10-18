package edu.purdue.whack;

import edu.purdue.whack.email.EmailService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MainController {

    private final EmailService emailService;

    // Actual file objects
    private final ObservableList<File> files;

    @FXML
    private Label removeItem;

    @FXML
    private Label sendingEmail;
    @FXML
    private ListView<File> listView;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private VBox dragDrop;

    @Inject
    public MainController(EmailService service) {
        this.emailService = service;
        files = FXCollections.observableArrayList();
    }

    @FXML
    public void onSubmitBtn(ActionEvent event) throws IOException {
        listView.setVisible(false);
        sendingEmail.setText("Sending Email ...");
        sendingEmail.setVisible(true);
        this.emailService.sendPrintRequest(this.files);
        this.files.clear();
        sendingEmail.setText("    Email Sent!");
        listView.setVisible(true);
;    }

    @FXML
    public void onDragOver(DragEvent event) {
        if (event.getGestureSource() != dragDrop && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    public void onDragDropped(DragEvent event) throws IOException {
        if (event.getDragboard().hasFiles()) {
            sendingEmail.setVisible(false);
            removeItem.setVisible(true);
            // Map list of dragFiles into paths
            List<File> dragFiles = event.getDragboard().getFiles();
            List<Path> dragFilePaths = dragFiles.stream()
                    .map(File::toPath)
                    .collect(Collectors.toList());

            // Get a list of only PDF dragFiles

            ArrayList<File> acceptedFiles = new ArrayList<>(dragFilePaths.size());
            for (int i = 0; i < dragFilePaths.size(); i++) {
                Path path = dragFilePaths.get(i);
                String fileMimeType = Files.probeContentType(path);
                // If file is a PDF, allow it
                if (fileMimeType.equals("application/pdf")) {
                    acceptedFiles.add(dragFiles.get(i));
                }
            }

            // If no files are PDF, then fail the drag request
            if (acceptedFiles.size() == 0) {
                event.setDropCompleted(false);
                return;
            }

            // Add the files to the path
            this.files.addAll(acceptedFiles);
            listView.setItems(files);

            event.setDropCompleted(true);
        } else {
            event.setDropCompleted(false);
        }
        event.consume();
    }

    @FXML
    public void logoutBtn(ActionEvent event) {
    }

    @FXML
    public void onChooseFileDialog(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Documents", "*.pdf")
        );
        Collection<File> files = chooser.showOpenMultipleDialog(parentPane.getScene().getWindow());
        this.files.addAll(files);
    }

    @FXML
    public void onMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            files.remove(listView.getSelectionModel().getSelectedItem());
            listView.setItems(files);
            if (files.isEmpty()) {
                removeItem.setVisible(false);
            }
        }
    }
}