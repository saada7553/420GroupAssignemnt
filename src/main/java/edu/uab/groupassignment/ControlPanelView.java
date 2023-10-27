package edu.uab.groupassignment;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

public class ControlPanelView {

    public final GridPane configPane = new GridPane();
    public final Label warningLabel = new Label();
    private final TextField nameTextField = new TextField();
    private final TextField priceTextField = new TextField();
    private final TextField locationXTextField = new TextField();
    private final TextField locationYTextField = new TextField();
    private final TextField widthTextField = new TextField();
    private final TextField heightTextField = new TextField();
    private final Button saveConfigBtn = new Button("Save");
    private final Button deleteConfigBtn = new Button("Delete");
    private final CheckBox addAsChildCheckBox = new CheckBox("Save Component as New Child");
    private final Button visitSelectedWithDroneBtn = new Button("Visit Selected with Drone");
    private final CheckBox isContainer = new CheckBox("Is Container");
    private final ItemController itemController;

    public ControlPanelView(ItemController itemController) {
        this.itemController = itemController;
        updateUI();
        updateConfigPanel();
        setupButtons();
    }

    public void updateUI() {
        addAsChildCheckBox.setDisable(!itemController.getSelectedItem().isContainer);
        isContainer.setDisable(!itemController.getSelectedItem().isContainer || !addAsChildCheckBox.isSelected());
        isContainer.setSelected(itemController.getSelectedItem().isContainer);
        nameTextField.setText(itemController.getSelectedItem().getName());
        priceTextField.setText(Double.toString(itemController.getSelectedItem().price));
        locationXTextField.setText(Double.toString(itemController.getSelectedItem().getX()));
        locationYTextField.setText(Double.toString(itemController.getSelectedItem().getY()));
        widthTextField.setText(Double.toString(itemController.getSelectedItem().getWidth()));
        heightTextField.setText(Double.toString(itemController.getSelectedItem().getHeight()));
    }

    private void setupButtons() {

        saveConfigBtn.setOnMouseClicked(event -> {
            if (itemController.getSelectedItem() == itemController.itemsRoot & !addAsChildCheckBox.isSelected()) {
                warningLabel.setText("You can not edit the root.");
            } else if (addAsChildCheckBox.isSelected() && itemController.getSelectedItem().isContainer) {
                saveNewItem();
            } else {
                updateExistingItem();
            }
            itemController.updateItems();
            itemController.expandTreeView(itemController.treeRoot);
        });

        deleteConfigBtn.setOnMouseClicked(event -> {
            if (itemController.getSelectedItem() == itemController.itemsRoot) {
                warningLabel.setText("Can not delete root");
            } else {
                FarmItem toDelete = itemController.getSelectedItem();
                FarmItem parentItem = toDelete.getParentItem();
                parentItem.removeChildItem(toDelete);
                itemController.setSelectedItem(itemController.itemsRoot);
                itemController.updateItems();
                itemController.expandTreeView(itemController.treeRoot);
                updateUI();
                warningLabel.setText("Deleted");
            }
        });

        addAsChildCheckBox.setOnMouseClicked(event -> isContainer.setDisable(!addAsChildCheckBox.isSelected()));
    }

    private void saveNewItem() {
        boolean saveResult = itemController.saveAsNewItem(
                isContainer.isSelected(),
                Double.parseDouble(locationXTextField.getText()),
                Double.parseDouble(locationYTextField.getText()),
                Double.parseDouble(widthTextField.getText()),
                Double.parseDouble(heightTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                nameTextField.getText(),
                itemController.getSelectedItem()
        );
        addAsChildCheckBox.setSelected(!saveResult);
        warningLabel
                .setText(saveResult ? "Successfully saved new item" :
                        "Could not save, ensure that new item fits inside parent container."
                );
    }

    private void updateExistingItem() {
        boolean fitsWithoutCollision = itemController.checkConstraintsWithParent(
                abs(Double.parseDouble(locationXTextField.getText())),
                abs(Double.parseDouble(locationYTextField.getText())),
                abs(Double.parseDouble(widthTextField.getText())),
                abs(Double.parseDouble(heightTextField.getText()))
        );

        if (fitsWithoutCollision) {
            itemController.getSelectedItem().setName(nameTextField.getText());
            itemController.getSelectedItem().price = Double.parseDouble(priceTextField.getText());
            double currentX = itemController.getSelectedItem().getX();
            double currentY = itemController.getSelectedItem().getY();
            double newX = abs(Double.parseDouble(locationXTextField.getText()));
            double newY = abs(Double.parseDouble(locationYTextField.getText()));
            double offsetX = newX - currentX;
            double offsetY = newY - currentY;
            itemController.recursiveLocationUpdate(offsetX, offsetY, itemController.getSelectedItem());
            itemController.getSelectedItem().setNewDimentions(
                    abs(Double.parseDouble(widthTextField.getText())),
                    abs(Double.parseDouble(heightTextField.getText()))
            );
        }
        warningLabel.setText(fitsWithoutCollision ? "Successfully updated item" :
                "Couldn't save changes, collision with parent container."
        );
    }

    private void updateConfigPanel() {
        configPane.setHgap(10);
        configPane.setVgap(10);

        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        Label locationXLabel = new Label("Location X:");
        Label locationYLabel = new Label("Location Y:");
        Label widthLabel = new Label("Width:");
        Label heightLabel = new Label("Height:");

        warningLabel.setTextFill(Color.RED);
        configPane.add(nameLabel, 0, 0);
        configPane.add(nameTextField, 1, 0);
        configPane.add(priceLabel, 0, 1);
        configPane.add(priceTextField, 1, 1);
        configPane.add(locationXLabel, 0, 2);
        configPane.add(locationXTextField, 1, 2);
        configPane.add(locationYLabel, 0, 3);
        configPane.add(locationYTextField, 1, 3);
        configPane.add(widthLabel, 0, 4);
        configPane.add(widthTextField, 1, 4);
        configPane.add(heightLabel, 0, 5);
        configPane.add(heightTextField, 1, 5);
        configPane.add(addAsChildCheckBox, 0, 6);
        configPane.add(isContainer, 1, 6);
        configPane.add(saveConfigBtn, 0, 7);
        configPane.add(deleteConfigBtn, 1, 7);
        configPane.add(visitSelectedWithDroneBtn, 0, 8);
    }
}
