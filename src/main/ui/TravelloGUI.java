package ui;

import model.Trip;
import model.ItineraryItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the graphical user interface (GUI) for the Travello application.
// Allows the user to view and manage the itinerary of a single trip.

public class TravelloGUI extends JFrame {

    private Trip currentTrip;
    private JPanel itineraryPanel;
    private JLabel titleLabel;
    private JTextField dateField;
    private JTextField startField;
    private JTextField endField;
    private JTextField locationField;

    private static final String JSON_STORE = "./data/trip.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs a new TravelPlannerGUI with an empty trip, initializes
    // all graphical components
    public TravelloGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("Travello");
        setSize(900, 600);

        BackgroundPanel backgroundPanel = new BackgroundPanel("./data/Background.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentTrip = new Trip("My Trip");
        initializeComponents();
        initializeTrip();
        refreshList();
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
    // EFFECTS: initializes and adds the top panel of the GUI.
    private void initializeInputPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setOpaque(false);

        titleLabel = new JLabel("Welcome to " + currentTrip.getName() + "!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        fieldsPanel.setOpaque(false);

        dateField = new JTextField();
        startField = new JTextField();
        endField = new JTextField();
        locationField = new JTextField();

        fieldsPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        fieldsPanel.add(new JLabel("Start (HH:MM):"));
        fieldsPanel.add(new JLabel("End (HH:MM):"));
        fieldsPanel.add(new JLabel("Location:"));
        fieldsPanel.add(new JLabel(""));

        addFieldInputs(fieldsPanel);

        topPanel.add(fieldsPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }

    // MODIFIES: panel
    // EFFECTS: adds the input text fields and add button to the given panel.
    private void addFieldInputs(JPanel panel) {
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(e -> addItem());

        panel.add(dateField);
        panel.add(startField);
        panel.add(endField);
        panel.add(locationField);
        panel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the itinerary display panel to the center of
    // the GUI.
    private void initializeListPanel() {
        itineraryPanel = new JPanel();
        itineraryPanel.setLayout(new BoxLayout(itineraryPanel, BoxLayout.Y_AXIS));
        itineraryPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(itineraryPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        JLabel itineraryLabel = new JLabel("Itinerary");
        itineraryLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        itineraryLabel.setForeground(Color.BLACK);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        centerPanel.add(itineraryLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the remove button panel to the bottom of the
    // GUI.
    private void initializeButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JButton removeButton = new JButton("Remove Item");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        removeButton.addActionListener(e -> removeItem());
        saveButton.addActionListener(e -> saveTrip());
        loadButton.addActionListener(e -> loadTrip());

        panel.add(removeButton);
        panel.add(saveButton);
        panel.add(loadButton);
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
    // EFFECTS: asks the user for an item number to remove; removes that itinerary
    // item if the number is valid and updates the displayed itinerary;
    // otherwise shows an error message.
    private void removeItem() {
        String input = JOptionPane.showInputDialog(this, "Enter item number to remove:");

        if (input == null) {
            return;
        }

        try {
            int number = Integer.parseInt(input.trim());
            int index = number - 1;

            if (index >= 0 && index < currentTrip.getItinerary().getItems().size()) {
                currentTrip.getItinerary().getItems().remove(index);
                refreshList();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid item number.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the displayed itinerary so that each itinerary item is shown
    // in its own numbered box.
    private void refreshList() {
        itineraryPanel.removeAll();

        int count = 1;
        for (ItineraryItem item : currentTrip.getItinerary().getItems()) {
            itineraryPanel.add(createItemBox(item, count));
            itineraryPanel.add(Box.createVerticalStrut(10));
            count++;
        }

        itineraryPanel.revalidate();
        itineraryPanel.repaint();
    }

    // EFFECTS: returns a panel displaying the given itinerary item in a numbered
    // box.
    private JPanel createItemBox(ItineraryItem item, int number) {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 255, 255, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        JLabel itemLabel = new JLabel("Item " + number);
        itemLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel locationLabel = new JLabel(item.getLocation());
        JLabel dateLabel = new JLabel(item.getDate().toString());
        JLabel timeLabel = new JLabel(item.getStartTime() + " - " + item.getEndTime());

        panel.add(itemLabel);
        panel.add(locationLabel);
        panel.add(dateLabel);
        panel.add(timeLabel);

        return panel;
    }

    private void saveTrip() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentTrip);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Trip saved successfully.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save trip.");
        }
    }

    private void loadTrip() {
        try {
            currentTrip = jsonReader.read();
            if (titleLabel != null) {
                titleLabel.setText("Welcome to " + currentTrip.getName() + "!");
            }
            refreshList();
            JOptionPane.showMessageDialog(this, "Trip loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load trip.");
            e.printStackTrace();
        }
    }

    private void initializeTrip() {
        String[] options = { "Create New Trip", "Load Trip" };

        int choice = JOptionPane.showOptionDialog(
                this,
                "Welcome to Travello",
                "Start",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 1) {
            loadTrip();
            if (currentTrip == null) {
                currentTrip = new Trip("My Trip");
            }
        } else {
            String tripName = JOptionPane.showInputDialog(this, "Enter trip name:");
            if (tripName == null || tripName.trim().isEmpty()) {
                tripName = "My Trip";
            }
            currentTrip = new Trip(tripName.trim());
        }
    }
}