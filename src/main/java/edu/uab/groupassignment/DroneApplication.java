package edu.uab.groupassignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DroneApplication extends Application {
    private DashboardSingleton dashboardSingleton = DashboardSingleton.getInstance();

    @Override
    public void start(Stage primaryStage) {
        dashboardSingleton.initAll();
        Scene scene = new Scene(dashboardSingleton.mainHBox,800,800);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}