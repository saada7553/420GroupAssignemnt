package edu.uab.groupassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DroneAppController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}