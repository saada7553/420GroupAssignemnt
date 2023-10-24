package edu.uab.groupassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DroneApplication extends Application {
    private static DroneApplication instance;

    public DroneApplication() { // Cannot make private as JavaFX requires it for Application
        super();
        synchronized (DroneApplication.class) {
            if (instance != null) {
                throw new UnsupportedOperationException(
                        getClass() + " is singleton, does not support multiple instances");
            }
            instance = this;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DroneApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Farm Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}