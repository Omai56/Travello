package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a packing checklist for a trip.
 * A checklist stores up to MAX_ITEMS ChecklistItem objects.
 * Items can be added, and the checklist can report packed/unpacked items.
 */
public class Checklist {

    public static final int MAX_ITEMS = 50;

    // EFFECTS: creates an empty checklist with capacity MAX_ITEMS.
    public Checklist() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a new checklist item with the given name.
    // The new item is initially not packed.
    // If the checklist is full, this method does nothing.
    public void addItem(String name) {
        // stub
    }

    // EFFECTS: returns true if the checklist is full (number of stored items ==
    // MAX_ITEMS);
    // otherwise returns false.
    public boolean isFull() {
        return false; // stub
    }

    // EFFECTS: returns true if all items in this checklist are packed
    // and the checklist is not empty; otherwise returns false.
    public boolean allPacked() {
        return false; // stub
    }

    // EFFECTS: returns an array containing all checklist items currently stored in
    // this checklist.
    public ArrayList<ChecklistItem> getChecklist() {
        return null; // stub
    }

    // EFFECTS: returns a String representation of all items in this checklist for
    // display in the Ui.
    // If the checklist is empty, returns "No checklist items."
    public String getChecklistString() {
        return null; // stub
    }

    // EFFECTS: returns a String containing all unpacked items in the order they
    // appear in the checklist.
    // If there are no unpacked items, returns null.
    public String getUnpackedItems() {
        return null; // stub
    }

    // EFFECTS: returns a String containing all packed items in the order they
    // appear in the checklist.
    // If there are no packed items, returns null.
    public String getPackedItems() {
        return null; // stub
    }
}
