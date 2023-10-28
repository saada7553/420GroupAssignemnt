package edu.uab.groupassignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DroneApplication extends Application {
    private final DashboardSingleton dashboardSingleton = DashboardSingleton.getInstance();

    @Override
    public void start(Stage primaryStage) {
        dashboardSingleton.init();
        Scene scene = new Scene(dashboardSingleton.getMainHBox(),800,800);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Farm Dashboard");
        primaryStage.show();


        DefaultItems.itemsRoot.setNewDimentions(
                dashboardSingleton.getMainHBox().getWidth() * 0.75,
                dashboardSingleton.getMainHBox().getHeight() - 4
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}