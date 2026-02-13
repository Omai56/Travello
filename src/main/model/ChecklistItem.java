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
        this.name = name;
        this.packed = false;
    }

    // EFFECTS: returns the name of this checklist item.
    public String getName() {
        return name;
    }

    // EFFECTS: returns true if this item is packed, false otherwise.
    public boolean isPacked() {
        return packed;
    }

    // MODIFIES: this
    // EFFECTS: sets this item’s packed status to true.
    public void markPacked() {
        packed = true;
    }

    // MODIFIES: this
    // EFFECTS: sets this item’s packed status to false.
    public void markUnpacked() {
        packed = false;
    }

    // EFFECTS: returns a String representation of this checklist item for display
    // in the UI,
    // including its name and packed status.
    public String getChecklistItem() {
        if (this.isPacked()) {
            return "[X] " + name;
        } else {
            return "[ ] " + name;
        }
    }
}
