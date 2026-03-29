package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a trip itinerary as a collection of itinerary items.
 * Provides operations to add and view itinerary items, and to detect scheduling
 * conflicts.
 */
public class Itinerary implements Writable {

    private List<ItineraryItem> items;

    // EFFECTS: constructs an empty itinerary.
    public Itinerary() {
        items = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds item to this itinerary and returns true if it does not conflict
    // with any
    // existing item; otherwise does not add it and returns false.
    public boolean addItem(ItineraryItem item) {
        if (hasConflict(item)) {
            return false;
        }
        items.add(item);

        EventLog.getInstance().logEvent(new Event("Added itinerary item: " + item.getLocation() + " on "
                + item.getDate() + " from " + item.getStartTime() + " to " + item.getEndTime()));
        return true;
    }

    public void removeItem(int index) {
        ItineraryItem removed = items.remove(index);

        EventLog.getInstance().logEvent(new Event("Removed itinerary item: " + removed.getLocation() + " on "
                + removed.getDate() + " from " + removed.getStartTime() + " to " + removed.getEndTime()));
    }

    // EFFECTS: returns true if item conflicts with at least one existing item in
    // this itinerary;
    // returns false otherwise.
    public boolean hasConflict(ItineraryItem item) {
        for (ItineraryItem i : items) {
            if (i.conflictsWith(item)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns a list of all itinerary items in this itinerary.
    public List<ItineraryItem> getItems() {
        return items;
    }

    // EFFECTS: returns a formatted string containing all itinerary items, one per
    // line.
    // If there are no items, returns "No itinerary items."
    public String printItinerary() {
        if (items.size() == 0) {
            return "No itinerary items.";
        }

        String result = "\n";
        int count = 1;
        for (ItineraryItem item : items) {
            result += "Itinerary" + count + ": \n" + item.getDisplayItem() + "\n";
            count++;
        }
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: returns items in this Itinerary as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ItineraryItem item : items) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }
}
