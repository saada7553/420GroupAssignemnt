package edu.uab.groupassignment;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DroneAnimController {

    // Create the Timelines
    Timeline rotate = new Timeline();
    Timeline moveDiagonal = new Timeline();
    Timeline moveUp = new Timeline();
    Timeline rotateNext = new Timeline();
    Timeline rotateLast = new Timeline();
    Timeline moveLeft = new Timeline();
    public SequentialTransition sequence = new SequentialTransition();

    public DroneAnimController() {
        anim();
    }

    public void anim() {
        DashboardSingleton inst = DashboardSingleton.getInstance();
        ImageView drone = new ImageView(new Image("/drone.png"));

        inst.getVisStackPane().getChildren().add(drone);

        // Get the Scene width and height along with image width
        double sceneWidth = inst.getVisualizerGroup().getLayoutBounds().getWidth();
        double sceneHeight = inst.getVisualizerGroup().getLayoutBounds().getHeight();
        double droneWidth = drone.getLayoutBounds().getWidth();

        // Define the Durations
        Duration startDuration = Duration.ZERO;
        Duration endDuration = Duration.seconds(5);
        Duration endDuration2 = Duration.seconds(2);

        // Create Key Frames
        KeyValue startKeyValue = new KeyValue(drone.translateXProperty(), 0);
        KeyFrame startKeyFrameDiagonal = new KeyFrame(startDuration, startKeyValue);
        KeyValue endKeyValueX = new KeyValue(drone.translateXProperty(), sceneWidth - droneWidth*1.5);
        KeyValue endKeyValueY = new KeyValue(drone.translateYProperty(), sceneHeight - droneWidth*2.375);
        KeyFrame endKeyFrameDiagonal = new KeyFrame(endDuration, endKeyValueX, endKeyValueY);


        KeyValue endKeyValueRotate = new KeyValue(drone.rotateProperty(), drone.getRotate() - 90);
        KeyFrame endKeyFrameRotate = new KeyFrame(endDuration2, endKeyValueRotate);

        KeyValue endKeyValueMoveUp = new KeyValue(drone.translateYProperty(), 0);
        KeyFrame endKeyFrameMoveUp = new KeyFrame(endDuration, endKeyValueMoveUp);

        KeyValue endKeyValueRotateNext = new KeyValue(drone.rotateProperty(),  drone.getRotate() - 180);
        KeyFrame endKeyFrameRotateNext = new KeyFrame(endDuration2, endKeyValueRotateNext);

        KeyValue endKeyValueMoveLeft = new KeyValue(drone.translateXProperty(), 0);
        KeyFrame endKeyFrameMoveLeft = new KeyFrame(endDuration, endKeyValueMoveLeft);

        KeyValue endKeyValueRotateLast = new KeyValue(drone.rotateProperty(),  drone.getRotate() - 360);
        KeyFrame endKeyFrameRotateLast = new KeyFrame(endDuration2, endKeyValueRotateLast);

        // Create Timelines
        rotate = new Timeline(endKeyFrameRotate);
        rotateNext = new Timeline(endKeyFrameRotateNext);
        moveDiagonal = new Timeline(startKeyFrameDiagonal, endKeyFrameDiagonal);
        moveUp = new Timeline(endKeyFrameMoveUp);
        moveLeft = new Timeline(endKeyFrameMoveLeft);
        rotateLast = new Timeline(endKeyFrameRotateLast);

        // Create Sequence
        sequence = new SequentialTransition(moveDiagonal, rotate, moveUp, rotateNext, moveLeft, rotateLast);
        // Let the animation run forever
        sequence.setCycleCount(Timeline.INDEFINITE);
    }
}
