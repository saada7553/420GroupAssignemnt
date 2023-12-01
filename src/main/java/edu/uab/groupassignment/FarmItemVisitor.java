package edu.uab.groupassignment;

import java.util.Objects;

public class FarmItemVisitor {
    public double getCollectivePurchasePrice(FarmItem root) {
        double total = root.getPrice();
        if (root.isContainer && !Objects.isNull(root.getContainedItems())) {
            for (FarmItem item : root.getContainedItems()) {
                total += getCollectivePurchasePrice(item);
            }
        }
        return total;
    }

    public double getCollectiveMarketPrice(FarmItem root) {
        double total = root.getMarketPrice();
        if (root.isContainer && !Objects.isNull(root.getContainedItems())) {
            for (FarmItem item : root.getContainedItems()) {
                total += getCollectiveMarketPrice(item);
            }
        }
        return total;
    }
}
