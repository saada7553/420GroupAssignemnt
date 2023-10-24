package edu.uab.groupassignment;

public class DashboardSingleton {

    // Singleton Declaration
    private static DashboardSingleton instance = new DashboardSingleton();

    // Private Constructor
    private DashboardSingleton() { }

    // Instance Variables
    private FarmItem currentSelectedItem;

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
