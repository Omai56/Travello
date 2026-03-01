package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a single trip.
 * A trip contains an itinerary, an expense log, and a checklist.
 */
public class Trip implements Writable {
    private String name;
    private Itinerary itinerary;
    private ExpenseLog expenseLog;
    private Checklist checklist;

    // EFFECTS: constructs a trip with the given name.
    // Initializes an empty itinerary, expense log,
    // and checklist for this trip.
    public Trip(String name) {
        this.name = name;
        this.itinerary = new Itinerary();
        this.expenseLog = new ExpenseLog();
        this.checklist = new Checklist();
    }

    // EFFECTS: returns the name of this trip.
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this trip to the given name.
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the itinerary associated with this trip.
    public Itinerary getItinerary() {
        return itinerary;
    }

    // EFFECTS: returns the expense log associated with this trip.
    public ExpenseLog getExpenseLog() {
        return expenseLog;
    }

    // EFFECTS: returns the checklist associated with this trip.
    public Checklist getChecklist() {
        return checklist;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("itinerary", itinerary.toJson());
        json.put("expenseLog", expenseLog.toJson());
        json.put("checklist", checklist.toJson());
        return json;
    }
}