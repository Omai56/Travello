package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single scheduled activity in a trip itinerary.
 * Each item has a date, start time, end time, location, and optional notes.
 */

public class ItineraryItem {

    // REQUIRES: date, startTime, endTime, location are not null;
    // MODIFIES: this
    // EFFECTS: constructs an itinerary item with the given date, start time, end
    // time,
    // and location; notes is set to null;
    // throws IllegalArgumentException if endTime is not strictly after startTime.
    public ItineraryItem(LocalDate date, LocalTime startTime, LocalTime endTime, String location) {
        // stub
    }

    // EFFECTS: returns true if this item conflicts with other.
    // Two items conflict if they are on the same date and their time intervals
    // overlap.
    // If one item ends exactly when the other begins, they do NOT conflict.
    public boolean conflictsWith(ItineraryItem other) {
        return false; // stub
    }

    // EFFECTS: returns the date of this itinerary item.
    public LocalDate getDate() {
        return LocalDate.of(2000, 1, 1); // stub
    }

    // EFFECTS: returns the start time of this itinerary item.
    public LocalTime getStartTime() {
        return LocalTime.of(0, 0); // stub
    }

    // EFFECTS: returns the end time of this itinerary item.
    public LocalTime getEndTime() {
        return LocalTime.of(0, 0); // stub
    }

    // EFFECTS: returns the location of this itinerary item.
    public String getLocation() {
        return ""; // stub
    }

    // EFFECTS: returns the notes of this itinerary item; may be null.
    public String getNotes() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item's date to date.
    public void setDate(LocalDate date) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item's start time to startTime;
    // throws IllegalArgumentException if endTime is not strictly after startTime.
    public void setStartTime(LocalTime startTime) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item's end time to endTime;
    // throws IllegalArgumentException if endTime is not strictly after startTime.
    public void setEndTime(LocalTime endTime) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item's location to location.
    public void setLocation(String location) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this item's notes to notes (notes may be null).
    public void setNotes(String notes) {
        // stub
    }

    // EFFECTS: returns a formatted string representation of this itinerary item
    public String getDisplayItem() {
        return ""; // stub
    }
}
