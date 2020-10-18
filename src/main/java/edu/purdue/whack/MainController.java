package edu.purdue.whack;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

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

    private boolean dragDropSuccess;

    // Actual file objects
    private ObservableList<File> files;
    private ObservableList<String> filePaths;

    @FXML
    private Label dragLabel;

    @FXML
    private VBox dragDrop;

    @FXML
    private AnchorPane parentPane;

    public MainController() {
        files = FXCollections.observableArrayList();
        filePaths = FXCollections.observableArrayList();
        files.addListener((ListChangeListener<File>) change -> {
            if (change.wasRemoved()) {
                // If files were removed, and the new size is 0, replace listview with label
                if (files.size() == change.getRemovedSize()) {
                    // TODO replace with label
                }
            } else if (change.wasAdded()) {
                // TODO if label is showing, replace it out with ListView with filePaths as the object
                List<String> paths = change.getList().stream()
                        .map(File::getAbsolutePath)
                        .collect(Collectors.toList());

                filePaths.setAll(paths);
            }
        });
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

    public void onDragDropped(DragEvent event) throws IOException {
        if (event.getDragboard().hasFiles()) {
            // Map list of dragFiles into paths
            List<File> dragFiles = event.getDragboard().getFiles();
            List<Path> filePaths = dragFiles.stream()
                    .map(File::getAbsolutePath)
                    .map(URI::create)
                    .map(Path::of)
                    .collect(Collectors.toList());

            // Get a list of only PDF dragFiles
            ArrayList<File> acceptedFiles = new ArrayList<>(filePaths.size());
            for (int i = 0; i < filePaths.size(); i++) {
                Path path = filePaths.get(i);
                String fileMimeType = Files.probeContentType(path);
                // If file is a PDF, allow it
                if (fileMimeType.equals("application/pdf")) {
                    acceptedFiles.add(dragFiles.get(i));
                }
            }

            // If no files are PDF, then fail the drag request
            if (acceptedFiles.size() == 0) {
                event.setDropCompleted(false);
            }

            // Add the files to the path
            this.files.addAll(acceptedFiles);

            dragLabel.setText(event.getDragboard().getFiles().toString());
            dragLabel.setFont(new Font("System", 12));
            event.setDropCompleted(true);
        } else {
            event.setDropCompleted(false);
        }
        event.consume();
    }

    public void logoutBtn(ActionEvent event) {
    }

    public void onChooseFileDialog(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Documents", "application/pdf")
        );
        Collection<File> files = chooser.showOpenMultipleDialog(parentPane.getScene().getWindow());
        this.files.addAll(files);
    }
}