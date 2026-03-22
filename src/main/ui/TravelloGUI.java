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

    private static final String JSON_STORE = "./data/trip.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs a new TravelPlannerGUI with an empty trip, initializes
    // all graphical components
    public TravelloGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("Travello");
        setSize(600, 600);

        BackgroundPanel backgroundPanel = new BackgroundPanel("./data/Background1.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentTrip = new Trip("My Trip");
        initializeComponents();
        initializeTrip();
        refreshList();
        setVisible(true);
        showWelcomeGif();
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphical components of the GUI
    private void initializeComponents() {
        initializeInputPanel();
        initializeListPanel();
        initializeButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the welcome title panel to the top of the GUI.
    private void initializeInputPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        titleLabel = new JLabel("Welcome to " + currentTrip.getName() + "!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        ImageIcon icon = new ImageIcon("./data/tripicon.png");
        Image smallImage = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(smallImage));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(iconLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the itinerary section to the center of the GUI.
    private void initializeListPanel() {
        itineraryPanel = new JPanel();
        itineraryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        itineraryPanel.setOpaque(false);

        JLabel itineraryLabel = new JLabel("Itinerary");
        itineraryLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        itineraryLabel.setForeground(Color.BLACK);
        itineraryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addButton = new JButton("+ Add Itinerary");
        addButton.addActionListener(e -> addItem());

        JScrollPane scrollPane = new JScrollPane(itineraryPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(600, 380));

        JPanel itinerarySection = createItinerarySection(itineraryLabel, addButton, scrollPane);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(itinerarySection);

        add(centerPanel, BorderLayout.CENTER);
    }

    // EFFECTS: returns the panel containing the itinerary title, add-itinerary
    // button, and itinerary display.
    private JPanel createItinerarySection(JLabel itineraryLabel, JButton addButton, JScrollPane scrollPane) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(addButton);

        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel itinerarySection = new JPanel();
        itinerarySection.setLayout(new BoxLayout(itinerarySection, BoxLayout.Y_AXIS));
        itinerarySection.setOpaque(false);
        itinerarySection.add(itineraryLabel);
        itinerarySection.add(Box.createVerticalStrut(8));
        itinerarySection.add(buttonPanel);
        itinerarySection.add(Box.createVerticalStrut(12));
        itinerarySection.add(scrollPane);

        return itinerarySection;
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
    // EFFECTS: displays a welcome GIF on top of the main application window for
    // 2 seconds, then removes it.
    private void showWelcomeGif() {
        ImageIcon gifIcon = new ImageIcon("./data/welcome.gif");
        JLabel gifLabel = new JLabel(gifIcon);

        int gifWidth = gifIcon.getIconWidth();
        int gifHeight = gifIcon.getIconHeight();

        // int x = (getWidth() - gifWidth) / 2;
        // int y = (getHeight() - gifHeight) / 2;

        gifLabel.setBounds(160, 150, gifWidth, gifHeight);

        getLayeredPane().add(gifLabel, JLayeredPane.POPUP_LAYER);
        getLayeredPane().repaint();

        Timer timer = new Timer(2000, e -> {
            getLayeredPane().remove(gifLabel);
            getLayeredPane().repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: prompts the user for itinerary item information, tries to add new
    // item to the current trip.
    // If the item conflicts with an existing item or the input is invalid, shows a
    // message to the user.

    private void addItem() {
        try {
            ItineraryItem item = getItemFromUser();

            if (item == null) {
                return;
            }

            boolean success = currentTrip.getItinerary().addItem(item);

            if (!success) {
                JOptionPane.showMessageDialog(this, "Time conflict! Item not added.");
            }

            refreshList();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private ItineraryItem getItemFromUser() {
        String dateStr = JOptionPane.showInputDialog(this, "Enter date (YYYY-MM-DD):");
        if (dateStr == null) {
            return null;
        }
        String startStr = JOptionPane.showInputDialog(this, "Enter start time (HH:MM):");
        if (startStr == null) {
            return null;
        }
        String endStr = JOptionPane.showInputDialog(this, "Enter end time (HH:MM):");
        if (endStr == null) {
            return null;
        }
        String location = JOptionPane.showInputDialog(this, "Enter location:");
        if (location == null || location.trim().isEmpty()) {
            return null;
        }

        LocalDate date = LocalDate.parse(dateStr.trim());
        LocalTime start = LocalTime.parse(startStr.trim());
        LocalTime end = LocalTime.parse(endStr.trim());
        return new ItineraryItem(date, start, end, location.trim());
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
            updateTitle();
            refreshList();
            JOptionPane.showMessageDialog(this, "Trip loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load trip.");
            e.printStackTrace();
        }
    }

    private void updateTitle() {
        if (titleLabel != null) {
            titleLabel.setText("Welcome to " + currentTrip.getName() + "!");
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
            updateTitle();
        }
    }
}