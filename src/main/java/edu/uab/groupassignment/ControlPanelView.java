package edu.uab.groupassignment;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

public class ControlPanelView {

    public final GridPane configGrid = new GridPane();
    public final Label warningLabel = new Label();
    private final TextField nameTextField = new TextField();
    private final TextField priceTextField = new TextField();
    private final TextField marketPriceTextField = new TextField();
    private final TextField collectivePriceTextField = new TextField();
    private final TextField collectiveMarketPriceTextField = new TextField();
    private final TextField locationXTextField = new TextField();
    private final TextField locationYTextField = new TextField();
    private final TextField widthTextField = new TextField();
    private final TextField heightTextField = new TextField();
    private final Button saveConfigBtn = new Button("Save");
    private final Button deleteConfigBtn = new Button("Delete");
    private final CheckBox addChildCheckBox = new CheckBox("Save Component as New Child");
    private final Button visitSelectedBtn = new Button("Visit Selected with Drone");
    private final Button scanFarmBtn = new Button("Scan Farm with Drone");
    private final CheckBox isContainer = new CheckBox("Is Container");
    private final ItemController itemController;

    public ControlPanelView(ItemController itemController) {
        this.itemController = itemController;
        updateUI();
        updateConfigPanel();
        setupButtons();
    }

    public void updateUI() {
        addChildCheckBox.setDisable(!itemController.getSelectedItem().isContainer);
        isContainer.setDisable(!itemController.getSelectedItem().isContainer || !addChildCheckBox.isSelected());
        isContainer.setSelected(itemController.getSelectedItem().isContainer);

        nameTextField.setText(itemController.getSelectedItem().getName());

        priceTextField.setText(Double.toString(itemController.getSelectedItem().getPrice()));

        marketPriceTextField.setText(Double.toString(itemController.getSelectedItem().getMarketPrice()));
        marketPriceTextField.setDisable(isContainer.isSelected());

        collectivePriceTextField.setText(Double.toString(itemController.getSelectedItem().getCollectivePurchasePrice()));
        collectivePriceTextField.setEditable(false);

        collectiveMarketPriceTextField.setText(Double.toString(itemController.getSelectedItem().getCollectiveMarketPrice()));
        collectiveMarketPriceTextField.setEditable(false);

        locationXTextField.setText(Double.toString(itemController.getSelectedItem().getX()));
        locationYTextField.setText(Double.toString(itemController.getSelectedItem().getY()));

        widthTextField.setText(Double.toString(itemController.getSelectedItem().getWidth()));
        heightTextField.setText(Double.toString(itemController.getSelectedItem().getHeight()));
    }

    private void setupButtons() {
        visitSelectedBtn.setOnMouseClicked(event -> {
            DashboardSingleton.getInstance().getDroneAnimController().playVisitItem(DashboardSingleton.getInstance().itemController.getSelectedItem());
        });

        scanFarmBtn.setOnMouseClicked(event -> {
            DashboardSingleton.getInstance().getDroneAnimController().playScanFarm();
        });

        saveConfigBtn.setOnMouseClicked(event -> {
            FarmItem currItem = itemController.getSelectedItem();
            if (currItem == itemController.itemsRoot || currItem == DefaultItems.commandCentre) {
                warningLabel.setText(currItem == itemController.itemsRoot ?
                        "Can not delete root" :
                        "Can not delete drone station"
                );
            } else if (addChildCheckBox.isSelected() && itemController.getSelectedItem().isContainer) {
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

        addChildCheckBox.setOnMouseClicked(event -> isContainer.setDisable(!addChildCheckBox.isSelected()));
        isContainer.setOnMouseClicked(event -> marketPriceTextField.setDisable(isContainer.isSelected()));
    }

    private void saveNewItem() {
        boolean saveResult = itemController.saveAsNewItem(
                isContainer.isSelected(),
                Double.parseDouble(locationXTextField.getText()),
                Double.parseDouble(locationYTextField.getText()),
                Double.parseDouble(widthTextField.getText()),
                Double.parseDouble(heightTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                Double.parseDouble(marketPriceTextField.getText()),
                nameTextField.getText(),
                itemController.getSelectedItem()
        );
        addChildCheckBox.setSelected(!saveResult);
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
            itemController.getSelectedItem().setPrice(Double.parseDouble(priceTextField.getText()));
            double currentX = itemController.getSelectedItem().getX();
            double currentY = itemController.getSelectedItem().getY();
            double newX = abs(Double.parseDouble(locationXTextField.getText()));
            double newY = abs(Double.parseDouble(locationYTextField.getText()));
            double offsetX = newX - currentX;
            double offsetY = newY - currentY;
            itemController.recursiveLocationUpdate(offsetX, offsetY, itemController.getSelectedItem());
            itemController.getSelectedItem().setNewDimensions(
                    abs(Double.parseDouble(widthTextField.getText())),
                    abs(Double.parseDouble(heightTextField.getText()))
            );
        }
        warningLabel.setText(fitsWithoutCollision ? "Successfully updated item" :
                "Couldn't save changes, collision with parent container."
        );
    }

    private void updateConfigPanel() {
        configGrid.setHgap(10);
        configGrid.setVgap(10);

        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Item Purchase Price:");
        Label marketPriceLabel = new Label("Item Market Value:");
        Label collectivePriceLabel = new Label("Collective Purchase Price:");
        Label collectiveMarketPriceLabel = new Label("Collective Market Value:");
        Label locationXLabel = new Label("Location X:");
        Label locationYLabel = new Label("Location Y:");
        Label widthLabel = new Label("Width:");
        Label heightLabel = new Label("Height:");

        warningLabel.setTextFill(Color.RED);
        configGrid.add(nameLabel, 0, 0);
        configGrid.add(nameTextField, 1, 0);
        configGrid.add(priceLabel, 0, 1);
        configGrid.add(priceTextField, 1, 1);
        configGrid.add(marketPriceLabel, 0, 2);
        configGrid.add(marketPriceTextField, 1, 2);
        configGrid.add(collectivePriceLabel, 0, 3);
        configGrid.add(collectivePriceTextField, 1, 3);
        configGrid.add(collectiveMarketPriceLabel, 0, 4);
        configGrid.add(collectiveMarketPriceTextField, 1, 4);
        configGrid.add(locationXLabel, 0, 5);
        configGrid.add(locationXTextField, 1, 5);
        configGrid.add(locationYLabel, 0, 6);
        configGrid.add(locationYTextField, 1, 6);
        configGrid.add(widthLabel, 0, 7);
        configGrid.add(widthTextField, 1, 7);
        configGrid.add(heightLabel, 0, 8);
        configGrid.add(heightTextField, 1, 8);
        configGrid.add(addChildCheckBox, 0, 9);
        configGrid.add(isContainer, 1, 9);
        configGrid.add(saveConfigBtn, 0, 10);
        configGrid.add(deleteConfigBtn, 1, 10);
        configGrid.add(visitSelectedBtn, 0, 11);
        configGrid.add(scanFarmBtn, 1, 11);
    }
}
