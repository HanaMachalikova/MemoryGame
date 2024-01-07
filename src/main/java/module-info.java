module com.memorygame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.memorygame to javafx.fxml;
    exports com.memorygame;
}