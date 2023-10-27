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
            50, 50,
            250,
            100,
            50,
            "Farm",
            itemsRoot
    );

    public static final FarmItem cow = new FarmItem(
            false,
            90,
            80,
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

    public static void setUpItems() {
        farm.setParent(itemsRoot);
        itemsRoot.addChildItem(farm);
        farm.addChildItem(cow);
        cow.setParent(farm);
        itemsRoot.addChildItem(silo);
        silo.setParent(itemsRoot);
    }
}
