package edu.uab.groupassignment;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class FarmItem extends Group {
    private double price;
    private double marketPrice;
    public boolean isContainer;
    private String name;
    private final Rectangle mainRect;
    private final Text itemLabel;
    private FarmItem parentItem;

    private ArrayList<FarmItem> containedItems;

    public FarmItem(String name, boolean isContainer,
                    double x,
                    double y,
                    double width,
                    double height,
                    double price,
                    double marketPrice
    ) {
        this.isContainer = isContainer;
        if (isContainer) {
            containedItems = new ArrayList<>();
        }
        this.price = price;
        if (isContainer) {
            this.marketPrice = 0;
        } else {
            this.marketPrice = marketPrice;
        }
        this.name = name;

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
        item.parentItem = this;
        containedItems.add(item);
        this.getChildren().add(item);
    }

    public void removeChildItem(FarmItem item) {
        containedItems.remove(item);
        this.getChildren().remove(item);
    }

    public double[] acceptVisitor(FarmItemVisitor visitor) {
        double[] outArr = new double[2];
        outArr[0] = visitor.getCollectivePurchasePrice(this);
        outArr[1] = visitor.getCollectiveMarketPrice(this);
        return(outArr);
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

    public void setNewDimensions(double width, double height) {
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

    public FarmItem getParentItem() {
        return parentItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        if (!this.isContainer) {
            this.marketPrice = marketPrice;
        }
    }
}
