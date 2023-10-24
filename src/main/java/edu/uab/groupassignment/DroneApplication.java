package edu.uab.groupassignment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DroneApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox uiContainer = new VBox(10);
        Scene scene = new Scene(uiContainer,100,100);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}