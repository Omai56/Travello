package model;

/**
 * Represents a single item in a travel checklist.
 * A checklist item has a name and a packed/unpacked status.
 */
public class ChecklistItem {

    private String name;
    private boolean packed;

    // EFFECTS: creates a new checklist item with the given name and sets packed to
    // false.
    public ChecklistItem(String name) {
        // stub
    }

    // EFFECTS: returns the name of this checklist item.
    public String getName() {
        // stub
        return null;
    }

    // EFFECTS: returns true if this item has been packed, otherwise false.
    public boolean getPacked() {
        return false; // stub
    }

    // EFFECTS: returns true if this item is packed, false otherwise.
    public boolean isPacked() {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item’s packed status to true.
    public void markPacked() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item’s packed status to false.
    public void markUnpacked() {
        // stub
    }

    // EFFECTS: returns a String representation of this checklist item for display
    // in the UI,
    // including its name and packed status.
    public String getChecklistItem() {
        return null; // stub
    }
}
