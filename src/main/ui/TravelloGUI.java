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
    private JTextField dateField;
    private JTextField startField;
    private JTextField endField;
    private JTextField locationField;

    // EFFECTS: constructs a new TravelPlannerGUI with an empty trip, initializes
    // all graphical components
    public TravelloGUI() {
        currentTrip = new Trip("My Trip");

        setTitle("Travel Planner");
        setSize(900, 600);

        BackgroundPanel backgroundPanel = new BackgroundPanel("./data/Background.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphical components of the GUI
    private void initializeComponents() {
        initializeInputPanel();
        initializeListPanel();
        initializeButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the input panel with labels, text fields, and
    // the add button to the top of the GUI.
    private void initializeInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        inputPanel.setOpaque(false);

        dateField = new JTextField();
        startField = new JTextField();
        endField = new JTextField();
        locationField = new JTextField();

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(e -> addItem());

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(new JLabel("Start (HH:MM):"));
        inputPanel.add(new JLabel("End (HH:MM):"));
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(new JLabel(""));

        inputPanel.add(dateField);
        inputPanel.add(startField);
        inputPanel.add(endField);
        inputPanel.add(locationField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the itinerary list display to the center of the
    // GUI.
    private void initializeListPanel() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        list.setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the remove button panel to the bottom of the
    // GUI.
    private void initializeButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeItem());

        panel.add(removeButton);
        add(panel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: prompts the user for itinerary item information, tries to add new
    // item to the current trip.
    // If the item conflicts with an existing item or the input is invalid, shows a
    // message to the user.

    private void addItem() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            LocalTime start = LocalTime.parse(startField.getText().trim());
            LocalTime end = LocalTime.parse(endField.getText().trim());
            String location = locationField.getText().trim();

            ItineraryItem item = new ItineraryItem(date, start, end, location);
            boolean success = currentTrip.getItinerary().addItem(item);

            if (!success) {
                JOptionPane.showMessageDialog(this, "Time conflict! Item not added.");
            } else {
                dateField.setText("");
                startField.setText("");
                endField.setText("");
                locationField.setText("");
            }

            refreshList();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the selected itinerary item from the current trip
    // if one is selected, and updates the displayed list.
    private void removeItem() {
        int index = list.getSelectedIndex();

        if (index >= 0) {
            currentTrip.getItinerary().getItems().remove(index);
            refreshList();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the displayed list so that it shows all itinerary
    // items currently stored in the current trip.
    private void refreshList() {
        listModel.clear();

        for (ItineraryItem item : currentTrip.getItinerary().getItems()) {
            listModel.addElement(item.getDisplayItem());
        }
    }
}