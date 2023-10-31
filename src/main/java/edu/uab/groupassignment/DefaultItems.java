package edu.uab.groupassignment;

public class DefaultItems {

    public static final FarmItem itemsRoot = new FarmItem(
            true,
            0,
            0,
            800,
            600,
            50,
            "Root"
    );

    public static final FarmItem farm = new FarmItem(
            true,
            50, 300,
            250,
            100,
            50,
            "Farm"
    );

    public static final FarmItem cow = new FarmItem(
            false,
            90, 330,
            40, 20,
            50, "Cow"
    );

    public static final FarmItem silo = new FarmItem(
            true,
            400,
            150,
            90,
            300,
            50,
            "Silo"
    );


    public static final FarmItem commandCentre = new FarmItem(
            true,
            50,
            50,
            150,
            150,
            50,
            "Drone Command Centre"
    );

    public static void setUpItems() {
        itemsRoot.addChildItem(farm);
        itemsRoot.addChildItem(commandCentre);
        farm.addChildItem(cow);
        itemsRoot.addChildItem(silo);
    }
}
