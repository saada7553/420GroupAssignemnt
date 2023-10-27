package edu.uab.groupassignment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class DashboardSingleton {

    // Singleton Declaration
    private static final DashboardSingleton instance = new DashboardSingleton();

    // Singleton members
    public ItemController itemController;
    public ControlPanelView panelView;
    private HBox mainHBox;

    // Private Constructor
    private DashboardSingleton() {
        itemController = new ItemController();
        panelView = new ControlPanelView(itemController);
    }

    // Add the main section dedicated to visualization and mainHBox
    public void init() {
        Group visGroup = new Group(itemController.itemsRoot);
        VBox leftComponents = new VBox(
                20,
                itemController.itemsTree,
                panelView.warningLabel,
                panelView.configPane
        );
        visGroup.minHeight(700);
        visGroup.minWidth(700);
        leftComponents.setSpacing(20);
        leftComponents.setPadding(new Insets(20, 20, 20, 20));
        leftComponents.setAlignment(Pos.TOP_CENTER);
        mainHBox = new HBox(20, leftComponents, visGroup);
    }

    // Getters and Setters
    public static DashboardSingleton getInstance() {
        return instance;
    }

    public HBox getMainHBox() {
        return this.mainHBox;
    }
}
