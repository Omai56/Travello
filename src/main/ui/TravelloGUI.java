package ui;

import model.Trip;
import model.ItineraryItem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

// Represents the graphical user interface (GUI) for the Travello application.
// Allows the user to view and manage the itinerary of a single trip.

public class TravelloGUI extends JFrame {

    private Trip currentTrip;
    private DefaultListModel<String> listModel;
    private JList<String> list;

    // EFFECTS: constructs a new TravelPlannerGUI with an empty trip, initializes
    // all graphical components
    public TravelloGUI() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphical components of the GUI
    private void initializeComponents() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: prompts the user for itinerary item information, tries to add new
    // item to the current trip.
    // If the item conflicts with an existing item or the input is invalid, shows a
    // message to the user.

    private void addItem() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes the selected itinerary item from the current trip
    // if one is selected, and updates the displayed list.
    private void removeItem() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: updates the displayed list so that it shows all itinerary
    // items currently stored in the current trip.
    private void refreshList() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a visual image component to the GUI.
    private void addImage() {
        // stub
    }
}