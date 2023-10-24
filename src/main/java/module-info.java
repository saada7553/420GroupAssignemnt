module edu.uab.groupassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.uab.groupassignment to javafx.fxml;
    exports edu.uab.groupassignment;
}