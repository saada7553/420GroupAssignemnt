package edu.uab.groupassignment;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
        isContainer.setDisable(!addAsChildCheckBox.isSelected());
        this.itemController = itemController;
        updateUI();
        updateConfigPanel();
        setupButtons();
    }

    public void updateUI() {
        addAsChildCheckBox.setDisable(!itemController.getCurrentSelectedItem().isContainer);
        nameTextField.setText(itemController.getCurrentSelectedItem().getName());
        priceTextField.setText(Double.toString(itemController.getCurrentSelectedItem().price));
        locationXTextField.setText(Double.toString(itemController.getCurrentSelectedItem().getX()));
        locationYTextField.setText(Double.toString(itemController.getCurrentSelectedItem().getY()));
        widthTextField.setText(Double.toString(itemController.getCurrentSelectedItem().getWidth()));
        heightTextField.setText(Double.toString(itemController.getCurrentSelectedItem().getHeight()));
    }

    private void setupButtons() {

        saveConfigBtn.setOnMouseClicked(event -> {
            if (addAsChildCheckBox.isSelected() && itemController.getCurrentSelectedItem().isContainer) {
                itemController.saveAsNewItem(
                        isContainer.isSelected(),
                        Double.parseDouble(locationXTextField.getText()),
                        Double.parseDouble(locationYTextField.getText()),
                        Double.parseDouble(widthTextField.getText()),
                        Double.parseDouble(heightTextField.getText()),
                        Double.parseDouble(priceTextField.getText()),
                        nameTextField.getText()
                );
                addAsChildCheckBox.setSelected(false);
            } else {
                itemController.getCurrentSelectedItem().setName(nameTextField.getText());
                itemController.getCurrentSelectedItem().price = Double.parseDouble(priceTextField.getText());
                itemController.getCurrentSelectedItem().setNewCoordinates(
                        abs(Double.parseDouble(locationXTextField.getText())),
                        abs(Double.parseDouble(locationYTextField.getText()))
                );
                itemController.getCurrentSelectedItem().setNewDimentions(
                        abs(Double.parseDouble(widthTextField.getText())),
                        abs(Double.parseDouble(heightTextField.getText()))
                );
            }
            itemController.updateItems();
            itemController.expandTreeView(itemController.treeRoot);
        });

        deleteConfigBtn.setOnMouseClicked(event -> {

            FarmItem toDelete = itemController.getCurrentSelectedItem();
            FarmItem parentItem = toDelete.getItemParent();
            parentItem.removeChildItem(toDelete);
            itemController.updateItems();
            itemController.expandTreeView(itemController.treeRoot);
            warningLabel.setText("Deleted");

        });

        addAsChildCheckBox.setOnMouseClicked(event -> {
            isContainer.setDisable(!addAsChildCheckBox.isSelected());
        });

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
