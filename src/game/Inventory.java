package game;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    // Add an item to the inventory.
    public void addItem(Item item) {
        items.add(item);
    }

    // Remove one matching item after use.
    public void removeItem(String item) {
        for (Item inventoryItem : items) {
            if (inventoryItem.getName().equals(item)) {
                items.remove(inventoryItem);
                break;
            }
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
