package edu.uab.groupassignment;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DashboardController {
    @FXML
    private Label welcomeText;
    @FXML
    private TreeView itemsTree;
    private TreeItem treeRoot;
    private FarmItem itemsRoot;

    @FXML
    private ListView itemControls;
    @FXML
    private Group visGroup;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        for (int i = 0; i < 10; i++) {
            itemControls.getItems().add("Item " + i);
        }
        itemControls.setOnMouseClicked(event ->
                welcomeText.setText("Clicked on " + itemControls.getSelectionModel().getSelectedItem()));
        visGroup.getChildren().add(new FarmItem(false, 400, 300, 100, 120, 10, "thing"));
    }

    public void init() {
        System.out.println("tried init");
        itemsRoot = new FarmItem(true, 0, 0, 500, 500, 0, "Root");
        FarmItem farm = new FarmItem(true, 50, 50, 250, 100, 0, "Farm");
        itemsRoot.addChildItem(farm);
        FarmItem cow = new FarmItem(false, 10, 10, 10, 20, 50, "Cow");
        farm.addChildItem(cow);
        FarmItem silo = new FarmItem(true, 400, 400, 10, 100, 0, "Silo");
        itemsRoot.addChildItem(silo);

        treeRoot = new TreeItem("Root");
        for (Object e : itemsRoot.getContainedItems()) {
            populateTree(treeRoot, (FarmItem) e);
        }
        visGroup.getChildren().add(itemsRoot);
        itemsTree.setRoot(treeRoot);
        treeRoot.setExpanded(true);
    }

    private static void populateTree(TreeItem troot, FarmItem iroot) {
        TreeItem addedItem = new TreeItem(iroot.name);
        troot.getChildren().add(addedItem);
        if (iroot.isContainer) {
            for (Object e : iroot.getContainedItems()) {
                populateTree(addedItem, (FarmItem) e);
            }
        }
    }

}