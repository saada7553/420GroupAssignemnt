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
        this.itemsRoot = DefaultItems.itemsRoot;
        this.itemsTree = new TreeView<>();
        this.currentSelectedItem = itemsRoot;
        treeRoot = new TreeItem<>(itemsRoot);
        DefaultItems.setUpItems();
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
                DashboardSingleton
                        .getInstance()
                        .panelView.warningLabel
                        .setText("Selected " + currentSelectedItem.getName());
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
    public boolean saveAsNewItem(
            boolean isSelected,
            double locationX,
            double locationY,
            double width,
            double height,
            double price,
            String name
    ) {

        if (!checkConstraints(locationX, locationY, width, height)) {
            return false;
        }

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
        currentSelectedItem.addChildItem(newItem);
        currentSelectedItem = newItem;
        return true;
    }

    // Checks if new item would go out of bounds of the parent it is being placed in.
    private boolean checkConstraints(double locationX, double locationY, double width, double height) {
        double xStart = currentSelectedItem.getX();
        double yStart = currentSelectedItem.getY();
        double xLimit = xStart + currentSelectedItem.getWidth();
        double yLimit = yStart + currentSelectedItem.getHeight();
        return locationX + width <= xLimit && locationY + height <= yLimit && locationX >= xStart && locationY >= yStart;
    }

    // Checks if updating item would go out of bounds of parent.
    public boolean checkConstraintsWithParent(double locationX, double locationY, double width, double height) {
        double xStart = currentSelectedItem.getItemParent().getX();
        double yStart = currentSelectedItem.getItemParent().getY();
        double xLimit = xStart + currentSelectedItem.getItemParent().getWidth();
        double yLimit = yStart + currentSelectedItem.getItemParent().getHeight();
        return locationX + width <= xLimit && locationY + height <= yLimit && locationX >= xStart && locationY >= yStart;
    }

    // Updates location of current item as well as any children it contains.
    public void recursiveLocationUpdate(double xShift, double yShift, FarmItem item) {
        item.setNewCoordinates(item.getX() + xShift, item.getY() + yShift);

        if (!item.isContainer) {
            return;
        }

        for (FarmItem childItem : item.getContainedItems()) {
            recursiveLocationUpdate(xShift, yShift, childItem);
        }
    }

    // Getters and Setters
    public FarmItem getCurrentSelectedItem() {
        return this.currentSelectedItem;
    }

    public  void setCurrentSelectedItem(FarmItem newItem) {
        this.currentSelectedItem = newItem;
    }
}
