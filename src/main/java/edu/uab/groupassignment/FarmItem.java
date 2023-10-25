package edu.uab.groupassignment;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class FarmItem extends Group {
    public int price;
    public int x, y, width, height, length;
    public boolean isContainer;
    public String name;
    private ArrayList<FarmItem> containedItems;
    private Rectangle mainRect;
    private Text itemLabel;


    public FarmItem(boolean isContainer, int x, int y, int width, int height, int length, int price, String name) {
        this.isContainer = isContainer;
        if (isContainer) {
            containedItems = new ArrayList<>();
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.length = length;
        this.price = price;
        this.name = name;
        update();
    }

    public void update() {
        this.getChildren().clear();
        mainRect = new Rectangle(0, 0, width, height);

        setLayoutX(x);
        setLayoutY(y);

        mainRect.setStroke(Color.RED);
        mainRect.setStrokeWidth(4);
        mainRect.setFill(Color.TRANSPARENT);

        itemLabel = new Text(8, 15, name);
        this.getChildren().addAll(mainRect, itemLabel);
    }

    public ArrayList<FarmItem> getContainedItems() {
        return containedItems;
    }

    public void addChildItem(FarmItem item) {
        containedItems.add(item);
        this.getChildren().add(item);
    }

    @Override
    public String toString() {
        return name;
    }
}
