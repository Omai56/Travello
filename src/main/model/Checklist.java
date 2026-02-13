package model;

import java.util.ArrayList;

/**
 * Represents a packing checklist for a trip.
 * A checklist stores up to MAX_ITEMS ChecklistItem objects.
 * Items can be added, and the checklist can report packed/unpacked items.
 */
public class Checklist {

    public static final int MAX_ITEMS = 50;
    private ArrayList<ChecklistItem> items;

    // EFFECTS: creates an empty checklist with capacity MAX_ITEMS.
    public Checklist() {
        items = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new checklist item with the given name.
    // The new item is initially not packed.
    // If the checklist is full, this method does nothing.
    public void addItem(String name) {
        if (!isFull()) {
            items.add(new ChecklistItem(name));
        }
    }

    // EFFECTS: returns true if the checklist is full (number of stored items ==
    // MAX_ITEMS);
    // otherwise returns false.
    public boolean isFull() {
        return items.size() == MAX_ITEMS;
    }

    // EFFECTS: returns true if all items in this checklist are packed
    // and the checklist is not empty; otherwise returns false.
    public boolean allPacked() {
        if (items.isEmpty()) {
            return false;
        }
        for (ChecklistItem item : items) {
            if (!item.isPacked()) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: returns an array containing all checklist items currently stored in
    // this checklist.
    public ArrayList<ChecklistItem> getChecklist() {
        return items;
    }

    // EFFECTS: returns a String representation of all items in this checklist for
    // display in the Ui.
    // If the checklist is empty, returns "No checklist items."
    public String getChecklistString() {
        if (items.isEmpty()) {
            return "No checklist items.";
        }
        String result = "";
        for (int i = 0; i < items.size(); i++) {
            result += items.get(i).getChecklistItem();
            if (i < items.size() - 1) {
                result += "\n";
            }
        }
        return result;
    }

    // EFFECTS: returns a String containing all unpacked items in the order they
    // appear in the checklist.
    // If there are no unpacked items, returns null.
    public String getUnpackedItems() {
        String result = "";
        for (ChecklistItem i : items) {
            if (!i.isPacked()) {
                result += i.getChecklistItem() + "\n";
            }
        }
        return result;
    }

    // EFFECTS: returns a String containing all packed items in the order they
    // appear in the checklist.
    // If there are no packed items, returns null.
    public String getPackedItems() {
        String result = "";
        for (ChecklistItem i : items) {
            if (i.isPacked()) {
                result += i.getChecklistItem() + "\n";
            }
        }
        return result;
    }
}
