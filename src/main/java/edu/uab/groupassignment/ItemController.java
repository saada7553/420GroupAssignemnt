package edu.uab.groupassignment;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class ItemController {

    public final TreeView<FarmItem> itemsTree;
    public final FarmItem itemsRoot;
    public TreeItem<FarmItem> treeRoot;
    private FarmItem currentSelectedItem;

    public ItemController() {
        this.itemsRoot = new FarmItem(
                true,
                0,
                0,
                1000,
                600,
                50,
                "Root",
                null
        );
        this.itemsTree = new TreeView<>();
        this.currentSelectedItem = itemsRoot;
        treeRoot = new TreeItem<>(itemsRoot);
        testAdds();
        populateTree(treeRoot, itemsRoot);
        setupTree();
        updateItems();
        expandTreeView(treeRoot);
    }

    // Helper functions
    public void expandTreeView(TreeItem<FarmItem> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            for (TreeItem<FarmItem> child : item.getChildren()) {
                expandTreeView(child);
            }
        }
    }

    // Helper function for populating tree with all FarmItems in root
    private void populateTree(TreeItem<FarmItem> tRoot, FarmItem iRoot) {
        TreeItem<FarmItem> addedItem = new TreeItem<>(iRoot);
        tRoot.getChildren().add(addedItem);
        if (iRoot.isContainer) {
            for (FarmItem e : iRoot.getContainedItems()) {
                populateTree(addedItem, e);
            }
        }
    }

    // Initializes the Items tree, along with adding some test items/containers
    public void updateItems() {
        treeRoot = new TreeItem<>(itemsRoot);
        for (FarmItem item : itemsRoot.getContainedItems()) {
            populateTree(treeRoot, item);
        }
        itemsTree.setRoot(treeRoot);
    }

    private void setupTree() {
        // Set selected item by clicking in TreeView
        itemsTree.setOnMouseClicked(event -> {
            TreeItem<FarmItem> selTreeItem = itemsTree.getSelectionModel().getSelectedItem();
            if (selTreeItem != null) {
                currentSelectedItem = selTreeItem.getValue();
                DashboardSingleton.getInstance().panelView.updateUI();
            }
        });

        // Custom cell factory to display only the name in the TreeView
        itemsTree.setCellFactory(new Callback<>() {
            @Override
            public TreeCell<FarmItem> call(TreeView<FarmItem> param) {
                return new TreeCell<>() {
                    @Override
                    protected void updateItem(FarmItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
    }

    // Adds the item with the specifications as a child of the currentSelectedItem, draws it on screen.
    public void saveAsNewItem(
            boolean isSelected,
            double locationX,
            double locationY,
            double width,
            double height,
            double price,
            String name
    ) {
        FarmItem newItem = new FarmItem(
                isSelected,
                locationX,
                locationY,
                width,
                height,
                price,
                name,
                currentSelectedItem
        );

        newItem.setParent(currentSelectedItem);
        currentSelectedItem.addChildItem(newItem);
    }

    private void testAdds() {
        FarmItem farm = new FarmItem(
                true,
                50, 50,
                250,
                100,
                50,
                "Farm", itemsRoot
        );
        FarmItem cow = new FarmItem(
                false,
                90,
                20,
                40, 20,
                50, "Cow",
                farm
        );
        FarmItem silo = new FarmItem(
                true,
                400,
                150,
                90,
                300,
                50,
                "Silo", itemsRoot
        );

        farm.setParent(itemsRoot);
        itemsRoot.addChildItem(farm);
        farm.addChildItem(cow);
        cow.setParent(farm);
        itemsRoot.addChildItem(silo);
        silo.setParent(itemsRoot);
    }

    // Getters and Setters
    public FarmItem getCurrentSelectedItem() {
        return this.currentSelectedItem;
    }
}
