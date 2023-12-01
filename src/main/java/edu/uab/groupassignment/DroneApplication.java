package edu.uab.groupassignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DroneApplication extends Application {
    private final DashboardSingleton dashboardSingleton = DashboardSingleton.getInstance();

    @Override
    public void start(Stage primaryStage) {
        dashboardSingleton.init();
        Scene scene = new Scene(dashboardSingleton.getMainHBox(),1210,700);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Farm Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}