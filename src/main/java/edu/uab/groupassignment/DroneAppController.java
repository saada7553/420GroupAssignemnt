package edu.uab.groupassignment;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class DroneAppController {
    @FXML
    private Label welcomeText;
    @FXML
    private TreeView itemsTree;
    @FXML
    private ListView itemControls;
    @FXML
    private Group visGroup;

    private TreeItem rootItem;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        rootItem = new TreeItem("Root");
        TreeItem testItem = new TreeItem("Thing");
        rootItem.getChildren().add(testItem);
        testItem.getChildren().add(new TreeItem<>("Another thing"));
        itemsTree.setRoot(rootItem);

        for (int i = 0; i < 10; i++) {
            itemControls.getItems().add("Item " + i);
        }
        itemControls.setOnMouseClicked(event ->
                welcomeText.setText("Clicked on " + itemControls.getSelectionModel().getSelectedItem()));
        visGroup.getChildren().add(new DrawnItem(false, 400, 300, 100, 120, 10, "thing"));
    }

}