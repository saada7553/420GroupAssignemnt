package edu.uab.groupassignment;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class DrawnItem extends Group {
    private int price;
    private boolean isContainer;
    private String name;
    private ArrayList<DrawnItem> held_items;

    private Rectangle mainRect;
    private Text itemLabel;


    public DrawnItem(boolean isContainer, int x, int y, int width, int height, int price, String name){
        this.isContainer = isContainer;
        mainRect = new Rectangle(0, 0, width, height);
        this.price = price;
        this.name = name;

        setLayoutX(x);
        setLayoutY(y);

        mainRect.setStroke(Color.RED);
        mainRect.setStrokeWidth(4);
        mainRect.setFill(Color.TRANSPARENT);

        itemLabel = new Text(8, 15, name);
        this.getChildren().add(mainRect);
        this.getChildren().add(itemLabel);
    }
    public ArrayList<DrawnItem> getHeld_items() {
        return held_items;
    }

}
