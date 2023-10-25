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
    private static DashboardSingleton instance = new DashboardSingleton();

    // Private Constructor
    private DashboardSingleton() {
    }

    // Instance Variables
    private FarmItem currentSelectedItem;

    // Main components
    public HBox mainHBox;
    private VBox leftComponents;

    // Hierarchy components
    private TreeView<FarmItem> itemsTree = new TreeView<>();
    private FarmItem itemsRoot = new FarmItem(true, 0, 0, 500, 500, 50, 0, "Root");
    private TreeItem<FarmItem> treeRoot = new TreeItem(itemsRoot);

    // Config panel components
    private GridPane configPane = new GridPane();
    private TextField nameTextField;
    private TextField priceTextField;
    private TextField locationXTextField;
    private TextField locationYTextField;
    private TextField widthTextField;
    private TextField heightTextField;
    private TextField lengthTextField;
    private Button saveConfigBtn = new Button("Save");
    private Button deleteConfigBtn = new Button("Delete");

    // Visualizer components
    private Group visGroup;

    public void initAll() {
        testAdds();
        updateItems();

        // Create a custom cell factory to display only the name property
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
                            setText(item.name);
                        }
                    }
                };
            }
        });

        itemsTree.setOnMouseClicked(event -> {
            TreeItem<FarmItem> selTreeItem = itemsTree.getSelectionModel().getSelectedItem();
            if (selTreeItem != null) {
                currentSelectedItem = selTreeItem.getValue();
                nameTextField.setText(currentSelectedItem.name);
                priceTextField.setText(String.valueOf(currentSelectedItem.price));
                locationXTextField.setText(String.valueOf(currentSelectedItem.x));
                locationYTextField.setText(String.valueOf(currentSelectedItem.y));
                widthTextField.setText(String.valueOf(currentSelectedItem.width));
                heightTextField.setText(String.valueOf(currentSelectedItem.height));
                lengthTextField.setText(String.valueOf(currentSelectedItem.length));
            }
        });
        // Add the main section dedicated to visualization
        visGroup = new Group(itemsRoot);
        visGroup.minHeight(700);
        visGroup.minWidth(700);

        // Add the section dedicated to controls
        updateConfigPanel();
        saveConfigBtn.setOnMouseClicked(event -> {
            currentSelectedItem.name = nameTextField.getText();
            currentSelectedItem.price = Integer.valueOf(priceTextField.getText());
            currentSelectedItem.x = Integer.valueOf(locationXTextField.getText());
            currentSelectedItem.y = Integer.valueOf(locationYTextField.getText());
            currentSelectedItem.width = Integer.valueOf(widthTextField.getText());
            currentSelectedItem.height = Integer.valueOf(heightTextField.getText());
            currentSelectedItem.length = Integer.valueOf(lengthTextField.getText());
            updateItems();
        });
        leftComponents = new VBox(20, itemsTree, configPane, saveConfigBtn, deleteConfigBtn);
        leftComponents.setSpacing(20);
        leftComponents.setPadding(new Insets(20, 20, 20, 20));
        leftComponents.setAlignment(Pos.TOP_CENTER);

        // Add the main containing box
        mainHBox = new HBox(20, leftComponents, visGroup);
    }

    // Initializes the Items tree, along with adding some test items/containers
    private void updateItems() {
        for (FarmItem item : itemsRoot.getContainedItems()) {
            populateTree(treeRoot, item);
        }
        itemsTree.setRoot(treeRoot);
        treeRoot.setExpanded(true);
        currentSelectedItem = itemsRoot;
        visGroup = new Group(itemsRoot);
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

    private void testAdds() {
        FarmItem farm = new FarmItem(true, 50, 50, 250, 100, 50, 0, "Farm");
        itemsRoot.addChildItem(farm);
        FarmItem cow = new FarmItem(false, 90, 20, 40, 20, 50, 50, "Cow");
        farm.addChildItem(cow);
        FarmItem silo = new FarmItem(true, 400, 150, 90, 300, 50, 0, "Silo");
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
        Label lengthLabel = new Label("Length:");

        // TextFields for input
        nameTextField = new TextField(currentSelectedItem.name);
        priceTextField = new TextField(Integer.toString(currentSelectedItem.price));
        locationXTextField = new TextField(Integer.toString(currentSelectedItem.x));
        locationYTextField = new TextField(Integer.toString(currentSelectedItem.y));
        widthTextField = new TextField(Integer.toString(currentSelectedItem.width));
        heightTextField = new TextField(Integer.toString(currentSelectedItem.height));
        lengthTextField = new TextField(Integer.toString(currentSelectedItem.length));

        // Add labels and text fields to the GridPane
        configPane.add(nameLabel, 0, 0);
        configPane.add(nameTextField, 1, 0);
        configPane.add(priceLabel, 0, 1);
        configPane.add(priceTextField, 1, 1);
        configPane.add(locationXLabel, 0, 2);
        configPane.add(locationXTextField, 1, 2);
        configPane.add(locationYLabel, 0, 3);
        configPane.add(locationYTextField, 1, 3);
        configPane.add(lengthLabel, 0, 4);
        configPane.add(lengthTextField, 1, 4);
        configPane.add(widthLabel, 0, 5);
        configPane.add(widthTextField, 1, 5);
        configPane.add(heightLabel, 0, 6);
        configPane.add(heightTextField, 1, 6);
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
