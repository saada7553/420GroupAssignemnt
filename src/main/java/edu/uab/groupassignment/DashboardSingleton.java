package edu.uab.groupassignment;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashboardSingleton {

    // Singleton Declaration
    private static DashboardSingleton instance = new DashboardSingleton();
    // Private Constructor
    private DashboardSingleton() { }
    // Instance Variables
    private FarmItem currentSelectedItem;

    public HBox mainHBox;
    private VBox leftComponents;

    private TreeView itemsTree = new TreeView<>();
    private Pane configPane = new Pane();
    private TreeItem treeRoot;
    private FarmItem itemsRoot;

    private Group visGroup;

    public void initAll() {
        initItems();
        visGroup = new Group(itemsRoot);
        visGroup.minHeight(700);
        visGroup.minWidth(700);

        leftComponents = new VBox(20, itemsTree, configPane, new Button("nothing"));
        leftComponents.setSpacing(20);
        leftComponents.setPadding(new Insets(20,20,20,20));


        mainHBox = new HBox(20, leftComponents, visGroup);
    }

    // Initializes the Items tree, along with adding some test items/containers
    public void initItems() {
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
        itemsTree.setRoot(treeRoot);
        treeRoot.setExpanded(true);
    }
    //
    private static void populateTree(TreeItem troot, FarmItem iroot) {
        TreeItem addedItem = new TreeItem(iroot.name);
        troot.getChildren().add(addedItem);
        if (iroot.isContainer) {
            for (Object e : iroot.getContainedItems()) {
                populateTree(addedItem, (FarmItem) e);
            }
        }
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
