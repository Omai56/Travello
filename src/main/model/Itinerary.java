package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a trip itinerary as a collection of itinerary items.
 * Provides operations to add and view itinerary items, and to detect scheduling
 * conflicts.
 */
public class Itinerary {

    // EFFECTS: constructs an empty itinerary with no items.
    public Itinerary() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds item to this itinerary if it does not conflict with an existing
    // item;
    // otherwise throws IllegalArgumentException and this itinerary is unchanged.
    public void addItem(ItineraryItem item) {
        // stub
    }

    // EFFECTS: returns true if item conflicts with at least one existing item in
    // this itinerary;
    // returns false otherwise.
    public boolean hasConflict(ItineraryItem item) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: removes the first itinerary item equal to item from this itinerary
    // and returns true;
    // if no such item exists, returns false and this itinerary is unchanged.
    public boolean removeItem(ItineraryItem item) {
        return false; // stub
    }

    // EFFECTS: returns an unmodifiable list of all itinerary items in this
    // itinerary.
    // Changes to the returned list are not allowed and do not affect this
    // itinerary.
    public List<ItineraryItem> getItems() {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: returns a formatted String showing all itinerary items in this
    // itinerary,
    // one per line, in the current stored order.
    // If there are no items, returns a message indicating the itinerary is empty.
    public String printItinerary() {
        return ""; // stub
    }
}
