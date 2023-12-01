package edu.uab.groupassignment;

public class DefaultItems {

    public static final FarmItem itemsRoot = new FarmItem(
            "Root", true,
            0,
            0,
            800,
            600,
            50,
            50
    );

    public static final FarmItem farm = new FarmItem(
            "Farm", true,
            50,
            300,
            250,
            100,
            50,
            50
    );

    public static final FarmItem cow = new FarmItem(
            "Cow", false,
            90,
            330,
            40,
            20,
            50,
            50
    );

    public static final FarmItem silo = new FarmItem(
            "Silo", true,
            400,
            150,
            90,
            300,
            50,
            50
    );

    public static final FarmItem commandCentre = new FarmItem(
            "Drone Command Centre", true,
            50,
            50,
            150,
            150,
            50,
            50
    );

    public static final FarmItem drone = new FarmItem(
            "Drone", true,
            90,
            90,
            63,
            63,
            50,
            50
    );

    public static void setUpItems() {
        itemsRoot.addChildItem(farm);
        itemsRoot.addChildItem(commandCentre);
        farm.addChildItem(cow);
        itemsRoot.addChildItem(silo);
        commandCentre.addChildItem(drone);
    }
}
