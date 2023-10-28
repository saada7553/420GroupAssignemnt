package edu.uab.groupassignment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class Drone extends FarmItem {

    private final Image image = new Image("file:Images/drone.png");
    public final ImageView imageView = new ImageView(image);

    public Drone(
            double x,
            double y,
            FarmItem parent
    ) {
        super(false, x, y, 50, 50, 50, "Drone", parent);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setX(x);
        imageView.setY(y);
        getChildren().add(imageView);
    }

    @Override
    public void setNewCoordinates(Double x, Double y) {
        imageView.setX(x);
        imageView.setY(y);
        mainRect.setX(x);
        mainRect.setY(y);
        itemLabel.setX(x + 8);
        itemLabel.setY(y + 15);
    }

    public void moveToItem(double x, double y, Duration duration) {
        TranslateTransition transition = new TranslateTransition(duration, this);
        transition.setToX(x - this.getTranslateX());
        transition.setToY(y - this.getTranslateY());
        transition.play();
    }
}
