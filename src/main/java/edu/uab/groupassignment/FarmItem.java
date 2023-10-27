package edu.uab.groupassignment;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class FarmItem extends Group {
    public double price;
    public boolean isContainer;
    private String name;
    private final Rectangle mainRect;
    private final Text itemLabel;
    private FarmItem parentItem;

    private ArrayList<FarmItem> containedItems;

    public FarmItem(boolean isContainer,
                    double x,
                    double y,
                    double width,
                    double height,
                    double price,
                    String name,
                    FarmItem parent
    ) {
        this.isContainer = isContainer;
        if (isContainer) {
            containedItems = new ArrayList<>();
        }
        this.price = price;
        this.name = name;
        this.parentItem = parent;

        mainRect = new Rectangle(x, y, width, height);
        itemLabel = new Text(x + 8, y + 15, name);
        mainRect.setStroke(Color.GREEN);
        mainRect.setStrokeWidth(4);
        mainRect.setFill(Color.TRANSPARENT);
        this.getChildren().addAll(mainRect, itemLabel);
    }

    public ArrayList<FarmItem> getContainedItems() {
        return containedItems;
    }

    public void addChildItem(FarmItem item) {
        containedItems.add(item);
        this.getChildren().add(item);
    }

    public void removeChildItem(FarmItem item) {
        containedItems.remove(item);
    }

    @Override
    public String toString() {
        return name;
    }

    // Updates UI with new coordinates of item.
    public void setNewCoordinates(Double x, Double y) {
        mainRect.setX(x);
        mainRect.setY(y);
        itemLabel.setX(x + 8);
        itemLabel.setY(y + 15);
    }

    public void setNewDimentions(double width, double height) {
        mainRect.setWidth(width);
        mainRect.setHeight(height);
    }

    public Double getX() {
        return mainRect.getX();
    }

    public Double getY() {
        return mainRect.getY();
    }

    public Double getWidth() {
        return mainRect.getWidth();
    }

    public Double getHeight() {
        return mainRect.getHeight();
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
        itemLabel.setText(name);
    }

    public FarmItem getParentItem() { return parentItem; }
    public  void setParentItem(FarmItem parentItem) {
        this.parentItem = parentItem;
    }
}
