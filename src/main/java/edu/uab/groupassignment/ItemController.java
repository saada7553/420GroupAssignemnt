package edu.uab.groupassignment;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class ItemController {

    public final TreeView<FarmItem> itemsTree;
    public final FarmItem itemsRoot;
    public TreeItem<FarmItem> treeRoot;
    private FarmItem selectedItem;

    public ItemController() {
        this.itemsRoot = DefaultItems.itemsRoot;
        this.itemsTree = new TreeView<>();
        this.selectedItem = itemsRoot;
        treeRoot = new TreeItem<>(itemsRoot);
        DefaultItems.setUpItems();
        setupTree();
        updateItems();
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
        expandTreeView(treeRoot);
    }

    private void setupTree() {
        // Set selected item by clicking in TreeView
        itemsTree.setOnMouseClicked(event -> {
            // Select item
            TreeItem<FarmItem> selTreeItem = itemsTree.getSelectionModel().getSelectedItem();
            if (selTreeItem != null) {
                selectedItem = selTreeItem.getValue();
                DashboardSingleton.getInstance().panelView.updateUI();
                DashboardSingleton
                        .getInstance()
                        .panelView.warningLabel
                        .setText("Selected " + selectedItem.getName());
            }
            // Duplicate Item on RClick
            if (event.getButton().equals(javafx.scene.input.MouseButton.SECONDARY)) {
                DashboardSingleton.getInstance().panelView.warningLabel.setText("Duplicated " + selectedItem.getName());
                saveAsNewItem(selectedItem.isContainer,
                        selectedItem.getX(),
                        selectedItem.getY(),
                        selectedItem.getWidth(),
                        selectedItem.getHeight(),
                        selectedItem.price,
                        selectedItem.getName() + "Clone",
                        selectedItem.getParentItem());
                updateItems();
            }
        });

        // Make TreeItems display only the name in the TreeView
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
            boolean isContainer,
            double locationX,
            double locationY,
            double width,
            double height,
            double price,
            String name,
            FarmItem parent
    ) {

        if (!checkConstraints(locationX, locationY, width, height)) {
            return false;
        }

        FarmItem newItem = new FarmItem(
                isContainer,
                locationX,
                locationY,
                width,
                height,
                price,
                name,
                parent
        );
        parent.addChildItem(newItem);
        selectedItem = newItem;
        return true;
    }

    // Checks if new item would go out of bounds of the parent it is being placed in.
    private boolean checkConstraints(double locationX, double locationY, double width, double height) {
        double xStart = selectedItem.getX();
        double yStart = selectedItem.getY();
        double xLimit = xStart + selectedItem.getWidth();
        double yLimit = yStart + selectedItem.getHeight();
        return locationX + width <= xLimit && locationY + height <= yLimit && locationX >= xStart && locationY >= yStart;
    }

    // Checks if updating item would go out of bounds of parent.
    public boolean checkConstraintsWithParent(double locationX, double locationY, double width, double height) {
        double xStart = selectedItem.getParentItem().getX();
        double yStart = selectedItem.getParentItem().getY();
        double xLimit = xStart + selectedItem.getParentItem().getWidth();
        double yLimit = yStart + selectedItem.getParentItem().getHeight();
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
    public FarmItem getSelectedItem() {
        return this.selectedItem;
    }

    public void setSelectedItem(FarmItem newItem) {
        this.selectedItem = newItem;
    }
}
