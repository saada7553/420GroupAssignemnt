package edu.uab.groupassignment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class DashboardSingleton {

    // Singleton Declaration
    private static final DashboardSingleton instance = new DashboardSingleton();

    // Private Constructor
    private DashboardSingleton() {
        currentSelectedItem = itemsRoot;
        isContainer.setDisable(!addAsChildCheckBox.isSelected());
        isContainer.setAccessibleText("Muse be new item");
    }

    // Instance Variables
    private FarmItem currentSelectedItem;

    // Main components
    public HBox mainHBox;
    private VBox leftComponents;

    // Hierarchy components
    private TreeView<FarmItem> itemsTree = new TreeView<>();
    private FarmItem itemsRoot = new FarmItem(
            true,
            0,
            0,
            1000,
            600,
            50,
            "Root"
    );
    private TreeItem<FarmItem> treeRoot = new TreeItem(itemsRoot);

    // Config panel components
    private GridPane configPane = new GridPane();
    private TextField nameTextField;
    private TextField priceTextField;
    private TextField locationXTextField;
    private TextField locationYTextField;
    private TextField widthTextField;
    private TextField heightTextField;
    private final Button saveConfigBtn = new Button("Save");
    private final Button deleteConfigBtn = new Button("Delete");
    private final CheckBox addAsChildCheckBox = new CheckBox("Save Component as New Child");
    private final Button visitSelectedWithDroneBtn = new Button("Visited Selected With Drone");
    private final CheckBox isContainer = new CheckBox("Is Container");

    // Visualizer components
    private Group visGroup;

    public void initAll() {
        testAdds();
        updateItems();
        expandTreeView(treeRoot);

        // Create a custom cell factory to display only the name in the tree
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

        itemsTree.setOnMouseClicked(event -> {
            TreeItem<FarmItem> selTreeItem = itemsTree.getSelectionModel().getSelectedItem();
            if (selTreeItem != null) {
                currentSelectedItem = selTreeItem.getValue();
                addAsChildCheckBox.setDisable(!currentSelectedItem.isContainer);
                nameTextField.setText(currentSelectedItem.getName());
                priceTextField.setText(String.valueOf(currentSelectedItem.price));
                locationXTextField.setText(String.valueOf(currentSelectedItem.getX()));
                locationYTextField.setText(String.valueOf(currentSelectedItem.getY()));
                widthTextField.setText(String.valueOf(currentSelectedItem.getWidth()));
                heightTextField.setText(String.valueOf(currentSelectedItem.getHeight()));
                isContainer.setSelected(currentSelectedItem.isContainer);
            }
        });
        // Add the main section dedicated to visualization
        visGroup = new Group(itemsRoot);
        visGroup.minHeight(700);
        visGroup.minWidth(700);

        addAsChildCheckBox.setOnMouseClicked(event -> {
            isContainer.setDisable(!addAsChildCheckBox.isSelected());
        });

        // Add the section dedicated to controls
        updateConfigPanel();
        saveConfigBtn.setOnMouseClicked(event -> {
            if (addAsChildCheckBox.isSelected() && currentSelectedItem.isContainer) {
                saveAsNewItem();
                addAsChildCheckBox.setSelected(false);
            } else {
                currentSelectedItem.setName(nameTextField.getText());
                currentSelectedItem.price = Double.valueOf(priceTextField.getText());
                currentSelectedItem.setNewCoordinates(
                        Double.valueOf(locationXTextField.getText()),
                        Double.valueOf(locationYTextField.getText())
                );
                currentSelectedItem.setNewDimentions(
                        Double.valueOf(widthTextField.getText()),
                        Double.valueOf(heightTextField.getText())
                );
            }
            updateItems();
            expandTreeView(treeRoot);
        });

        leftComponents = new VBox(20, itemsTree, configPane);
        leftComponents.setSpacing(20);
        leftComponents.setPadding(new Insets(20, 20, 20, 20));
        leftComponents.setAlignment(Pos.TOP_CENTER);

        // Add the main containing box
        mainHBox = new HBox(20, leftComponents, visGroup);
    }

    // Initializes the Items tree, along with adding some test items/containers
    private void updateItems() {
        treeRoot = new TreeItem(itemsRoot);
        for (FarmItem item : itemsRoot.getContainedItems()) {
            populateTree(treeRoot, item);
        }
        itemsTree.setRoot(treeRoot);
    }

    // Adds the item with the specifications as a child of the currentSelectedItem, draws it on screen.
    private  void saveAsNewItem() {
        FarmItem newItem = new FarmItem(
                isContainer.isSelected(),
                Double.valueOf(locationXTextField.getText()),
                Double.valueOf(locationYTextField.getText()),
                Double.valueOf(widthTextField.getText()),
                Double.valueOf(heightTextField.getText()),
                Double.valueOf(priceTextField.getText()),
                nameTextField.getText()
        );

        currentSelectedItem.addChildItem(newItem);
        System.out.println(currentSelectedItem.getContainedItems());
    }

    // Helper function for populating tree with all FarmItems in root
    private static void populateTree(TreeItem troot, FarmItem iroot) {
        TreeItem<FarmItem> addedItem = new TreeItem<>(iroot);
        troot.getChildren().add(addedItem);
        if (iroot.isContainer) {
            for (Object e : iroot.getContainedItems()) {
                populateTree(addedItem, (FarmItem) e);
            }
        }
    }

    private void expandTreeView(TreeItem<FarmItem> item){
        if(item != null && !item.isLeaf()){
            item.setExpanded(true);
            for(TreeItem<FarmItem> child:item.getChildren()){
                expandTreeView(child);
            }
        }
    }

    private void testAdds() {
        FarmItem farm = new FarmItem(true, 50, 50, 250, 100, 50, "Farm");
        itemsRoot.addChildItem(farm);
        FarmItem cow = new FarmItem(false, 90, 20, 40, 20, 50, "Cow");
        farm.addChildItem(cow);
        FarmItem silo = new FarmItem(true, 400, 150, 90, 300, 50, "Silo");
        itemsRoot.addChildItem(silo);
    }

    private void updateConfigPanel() {
        configPane.setHgap(10);
        configPane.setVgap(10);

        // Labels for each field
        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        Label locationXLabel = new Label("Location-X:");
        Label locationYLabel = new Label("Location-Y:");
        Label widthLabel = new Label("Width:");
        Label heightLabel = new Label("Height:");

        // TextFields for input
        nameTextField = new TextField(currentSelectedItem.getName());
        priceTextField = new TextField(Double.toString(currentSelectedItem.price));
        locationXTextField = new TextField(Double.toString(currentSelectedItem.getX()));
        locationYTextField = new TextField(Double.toString(currentSelectedItem.getY()));
        widthTextField = new TextField(Double.toString(currentSelectedItem.getWidth()));
        heightTextField = new TextField(Double.toString(currentSelectedItem.getHeight()));

        // Add labels and text fields to the GridPane
        configPane.add(nameLabel, 0, 0);
        configPane.add(nameTextField, 1, 0);
        configPane.add(priceLabel, 0, 1);
        configPane.add(priceTextField, 1, 1);
        configPane.add(locationXLabel, 0, 2);
        configPane.add(locationXTextField, 1, 2);
        configPane.add(locationYLabel, 0, 3);
        configPane.add(locationYTextField, 1, 3);
        configPane.add(widthLabel, 0, 5);
        configPane.add(widthTextField, 1, 5);
        configPane.add(heightLabel, 0, 6);
        configPane.add(heightTextField, 1, 6);
        configPane.add(addAsChildCheckBox, 0, 7);
        configPane.add(isContainer, 1, 7);
        configPane.add(saveConfigBtn, 0, 8);
        configPane.add(deleteConfigBtn, 1, 8);
        configPane.add(visitSelectedWithDroneBtn, 0, 9);
    }

    // Getters and Setters
    public static DashboardSingleton getInstance() {
        return instance;
    }

    public void setCurrentSelectedItem(FarmItem item) {
        instance.currentSelectedItem = item;
    }

    public FarmItem getCurrentSelectedItem() {
        return instance.currentSelectedItem;
    }
}
