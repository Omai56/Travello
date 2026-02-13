package model;

/**
 * Represents a single trip.
 * A trip contains an itinerary, an expense log, and a checklist.
 */
public class Trip {

    // EFFECTS: constructs a trip with the given name.
    // Initializes an empty itinerary, expense log,
    // and checklist for this trip.
    public Trip(String name) {
        // stub
    }

    // EFFECTS: returns the name of this trip.
    public String getName() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this trip to the given name.
    public void setName(String name) {
        // stub
    }

    // EFFECTS: returns the itinerary associated with this trip.
    public Itinerary getItinerary() {
        return null;
    }

    // EFFECTS: returns the expense log associated with this trip.
    public ExpenseLog getExpenseLog() {
        return null;
    }

    // EFFECTS: returns the checklist associated with this trip.
    public Checklist getChecklist() {
        return null;
    }
}