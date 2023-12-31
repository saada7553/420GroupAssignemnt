package edu.uab.groupassignment;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DroneAnimController {

    private SequentialTransition scanFarmSeq = new SequentialTransition();
    private ImageView drone;
    private DashboardSingleton inst;

    private double farmWidth, farmHeight;

    public DroneAnimController() {
        init();
    }

    public void init() {
        inst = DashboardSingleton.getInstance();
        drone = new ImageView(new Image("/drone.png"));
        drone.setTranslateX(inst.itemController.commandCentre.getBoundsInLocal().getCenterX() - (drone.getBoundsInLocal().getHeight() / 2));
        drone.setTranslateY(inst.itemController.commandCentre.getBoundsInLocal().getCenterX() - (drone.getBoundsInLocal().getHeight() / 2));

        inst.getVisStackPane().getChildren().add(drone);
    }

    public void playScanFarm() {
        // Get the Scene width and height along with image width
        farmWidth = inst.getVisualizerGroup().getBoundsInLocal().getWidth();
        farmHeight = inst.getVisualizerGroup().getBoundsInLocal().getHeight();

        scanFarmSeq = createFarmScanSequence();
        scanFarmSeq.setCycleCount(1);
        scanFarmSeq.play();
    }

    public void playVisitItem(Group item) {
        TranslateTransition gotoItem = new TranslateTransition(Duration.seconds(1), drone);
        gotoItem.setToX(item.getBoundsInLocal().getCenterX() - (drone.getBoundsInLocal().getHeight() / 2));
        gotoItem.setToY(item.getBoundsInLocal().getCenterY() - (drone.getBoundsInLocal().getHeight() / 2));
        gotoItem.play();
    }

    private SequentialTransition createFarmScanSequence() {
        TranslateTransition gotoStart = new TranslateTransition(Duration.seconds(1), drone);
        gotoStart.setToX(0);
        gotoStart.setToY(0);
        SequentialTransition sequence = new SequentialTransition(gotoStart);
        double droneHeight = drone.getBoundsInLocal().getHeight();
        int numSteps = Math.floorDiv((int) (farmHeight - droneHeight), (int) drone.getBoundsInLocal().getHeight());

        double toX, toY = 0;
        for (int i = 0; i < numSteps; i++) {
            toX = i % 2 == 0 ? farmWidth - droneHeight : 0;
            toY = toY + droneHeight;

            TranslateTransition step = new TranslateTransition(Duration.seconds(2), drone);
            step.setToX(toX);
            step.setToY(toY);
            sequence.getChildren().add(step);
        }
        TranslateTransition returntoCentre = new TranslateTransition(Duration.seconds(1), drone);
        returntoCentre.setToX(inst.itemController.commandCentre.getBoundsInLocal().getCenterX() - (drone.getBoundsInLocal().getHeight() / 2));
        returntoCentre.setToY(inst.itemController.commandCentre.getBoundsInLocal().getCenterX() - (drone.getBoundsInLocal().getHeight() / 2));
        sequence.getChildren().add(returntoCentre);
        return sequence;
    }
}
