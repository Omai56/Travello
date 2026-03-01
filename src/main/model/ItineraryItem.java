package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single scheduled activity in a trip itinerary.
 * Each item has a date, start time, end time, location, and optional notes.
 */

public class ItineraryItem implements Writable {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String notes;

    // REQUIRES: date, startTime, endTime, location are not null;
    // MODIFIES: this
    // EFFECTS: constructs an itinerary item with the given date, start time, end
    // time,
    // and location; notes is set to null;
    // throws IllegalArgumentException if endTime is not strictly after startTime.
    public ItineraryItem(LocalDate date, LocalTime startTime, LocalTime endTime, String location) {
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.notes = null;
    }

    // EFFECTS: returns true if this item conflicts with other.
    // Two items conflict if they are on the same date and their time intervals
    // overlap.
    // If one item ends exactly when the other begins, they do NOT conflict.
    public boolean conflictsWith(ItineraryItem other) {
        if (!this.date.equals(other.date)) {
            return false;
        }
        return this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime);
    }

    // EFFECTS: returns the date of this itinerary item.
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns the start time of this itinerary item.
    public LocalTime getStartTime() {
        return startTime;
    }

    // EFFECTS: returns the end time of this itinerary item.
    public LocalTime getEndTime() {
        return endTime;
    }

    // EFFECTS: returns the location of this itinerary item.
    public String getLocation() {
        return location;
    }

    // EFFECTS: returns the notes of this itinerary item; may be null.
    public String getNotes() {
        return notes;
    }

    // MODIFIES: this
    // EFFECTS: sets this item's date to date.
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: sets this item's start time to startTime;
    // throws IllegalArgumentException if endTime is not strictly after startTime.
    public void setStartTime(LocalTime startTime) {
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.startTime = startTime;
    }

    // MODIFIES: this
    // EFFECTS: sets this item's end time to endTime;
    // throws IllegalArgumentException if endTime is not strictly after startTime.
    public void setEndTime(LocalTime endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        this.endTime = endTime;
    }

    // MODIFIES: this
    // EFFECTS: sets this item's location to location.
    public void setLocation(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: sets this item's notes to notes (notes may be null).
    public void setNotes(String notes) {
        this.notes = notes;
    }

    // EFFECTS: returns a formatted string representation of this itinerary item
    public String getDisplayItem() {
        String result = "Date: " + date + "\n"
                + "Time: " + startTime + "-" + endTime + "\n"
                + "Location: " + location + "\n";
        if (notes != null) {
            result += "Notes: " + notes;
        }
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date.toString());
        json.put("startTime", startTime.toString());
        json.put("endTime", endTime.toString());
        json.put("location", location);

        if (notes != null) {
            json.put("notes", notes);
        }

        return json;
    }
}
