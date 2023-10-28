package edu.uab.groupassignment;

public class DefaultItems {

    public static final FarmItem itemsRoot = new FarmItem(
            true,
            0,
            0,
            1000,
            600,
            50,
            "Root",
            null
    );

    public static final FarmItem farm = new FarmItem(
            true,
            50, 300,
            250,
            100,
            50,
            "Farm",
            itemsRoot
    );

    public static final FarmItem cow = new FarmItem(
            false,
            90,
            330,
            40, 20,
            50, "Cow",
            farm
    );

    public static final FarmItem silo = new FarmItem(
            true,
            400,
            150,
            90,
            300,
            50,
            "Silo",
            itemsRoot
    );

    public static final FarmItem droneControlCenter = new FarmItem(
            true,
            50,
            50,
            150,
            150,
            50,
            "Drone Control Station",
            itemsRoot
    );

    public static final Drone drone = new Drone(
            100,
            100,
            droneControlCenter
    );

    public static void setUpItems() {
        itemsRoot.addChildItem(droneControlCenter);
        droneControlCenter.addChildItem(drone);
        itemsRoot.addChildItem(farm);
        farm.addChildItem(cow);
        itemsRoot.addChildItem(silo);
    }
}
