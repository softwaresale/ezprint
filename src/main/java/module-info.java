module edu.purdue.whack {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.purdue.whack to javafx.fxml;
    exports edu.purdue.whack;
}