package edu.uab.groupassignment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import static java.lang.Math.abs;

public class DashboardSingleton {

    // Singleton Declaration
    private static final DashboardSingleton instance = new DashboardSingleton();
    public ItemController itemController;
    public ControlPanelView panelView;

    // Private Constructor
    private DashboardSingleton() {
        itemController = new ItemController();
        panelView = new ControlPanelView(itemController);
    }

    // Main component
    private HBox mainHBox;

    public void init() {
        // Add the main section dedicated to visualization
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

        // Add the main containing box
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
