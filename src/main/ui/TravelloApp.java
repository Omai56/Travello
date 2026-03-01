package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TravelloApp {

    private static final String JSON_STORE = "./data/trip.json";
    private Scanner input;
    private Trip trip;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public TravelloApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public void run() {
        initializeTrip();

        boolean keepGoing = true;
        while (keepGoing) {
            displayMainMenu();
            String command = input.nextLine().trim().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("i")) {
                doItineraryMenu();
            } else if (command.equals("e")) {
                doExpenseMenu();
            } else if (command.equals("c")) {
                doChecklistMenu();
            } else if (command.equals("s")) {
                saveTrip();
            } else {
                System.out.println("Invalid option.");
            }
        }
        System.out.println("Thank you for using Travello!");
    }

    private void initializeTrip() {
        System.out.println("Welcome to Travello!");
        System.out.print("Do you want to load an existing trip? (y/n): ");
        String choice = input.nextLine().trim().toLowerCase();

        if (choice.equals("y")) {
            loadTrip();

            // If loading failed and trip is still null,
            // create a new trip instead
            if (trip == null) {
                System.out.println("Creating a new trip instead.");
                createNewTrip();
            }

        } else {
            createNewTrip();
        }
    }

    private void createNewTrip() {
        System.out.print("Enter trip name: ");
        String name = input.nextLine();
        trip = new Trip(name);
    }

    private void displayMainMenu() {
        System.out.println("\n=== Main Menu (" + trip.getName() + ") ===");
        System.out.println("i -> Itinerary");
        System.out.println("e -> Expenses");
        System.out.println("c -> Checklist");
        System.out.println("s -> Save trip to file");
        System.out.println("q -> Quit");
        System.out.print("Choose: ");
    }

    // ITINERARY

    private void doItineraryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Itinerary Menu ===");
            System.out.println("1 -> Add itinerary item");
            System.out.println("2 -> View itinerary");
            System.out.println("b -> Back");
            System.out.print("Choose: ");

            String command = input.nextLine().trim().toLowerCase();
            if (command.equals("1")) {
                addItineraryItem();
            } else if (command.equals("2")) {
                System.out.println(trip.getItinerary().printItinerary());
            } else if (command.equals("b")) {
                back = true;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void addItineraryItem() {
        try {
            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(input.nextLine().trim());

            System.out.print("Start time (HH:MM): ");
            LocalTime start = LocalTime.parse(input.nextLine().trim());

            System.out.print("End time (HH:MM): ");
            LocalTime end = LocalTime.parse(input.nextLine().trim());

            System.out.print("Location: ");
            String location = input.nextLine();

            ItineraryItem item = new ItineraryItem(date, start, end, location);

            boolean added = trip.getItinerary().addItem(item);
            if (!added) {
                System.out.println("Conflict detected: item was NOT added.");
            } else {
                System.out.println("Item added.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // EXPENSES
    private void displayExpenseMenu() {
        System.out.println("\n=== Expenses Menu (" + trip.getName() + ") ===");
        System.out.println("1 -> Add expense");
        System.out.println("2 -> View expenses");
        System.out.println("3 -> View total spent");
        System.out.println("4 -> View category summary");
        System.out.println("5 -> Set budget");
        System.out.println("b -> Back");
        System.out.print("Choose: ");
    }

    private void doExpenseMenu() {
        boolean back = false;
        while (!back) {
            displayExpenseMenu();
            String command = input.nextLine().trim().toLowerCase();
            if (command.equals("1")) {
                addExpense();
            } else if (command.equals("2")) {
                System.out.println(trip.getExpenseLog().printExpenses());
            } else if (command.equals("3")) {
                System.out.println("Total spent: " + trip.getExpenseLog().totalSpent());
            } else if (command.equals("4")) {
                System.out.println(trip.getExpenseLog().categorySummary());
            } else if (command.equals("5")) {
                setBudget();
            } else if (command.equals("b")) {
                back = true;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void addExpense() {
        try {
            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(input.nextLine().trim());

            System.out.print("Amount (> 0): ");
            double amount = Double.parseDouble(input.nextLine().trim());

            System.out.println("Category options:");
            for (ExpenseCategory c : ExpenseCategory.values()) {
                System.out.println("- " + c);
            }
            System.out.print("Category: ");
            ExpenseCategory category = ExpenseCategory.valueOf(input.nextLine().trim().toUpperCase());

            System.out.print("Description: ");
            String description = input.nextLine();

            Expense expense = new Expense(date, amount, category, description);
            trip.getExpenseLog().addExpense(expense);

            System.out.println("Expense added.");

            budgetCheck();

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid expense (amount must be > 0, budget must be >= 0, category must match).");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private void budgetCheck() {
        if (trip.getExpenseLog().isOverBudget()) {
            System.out.println("WARNING: Budget exceeded!");
        } else if (trip.getExpenseLog().isNearBudget()) {
            System.out.println("WARNING: You are close to your budget limit.");
        }
    }

    private void setBudget() {
        try {
            System.out.print("Enter budget (>= 0): ");
            double budget = Double.parseDouble(input.nextLine().trim());
            trip.getExpenseLog().setBudget(budget);
            System.out.println("Budget set to: " + trip.getExpenseLog().getBudget());
        } catch (IllegalArgumentException e) {
            System.out.println("Budget must be >= 0.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // CHECKLIST

    private void displayChecklistMenu() {
        System.out.println("\n=== Checklist Menu ===");
        System.out.println("1 -> Add checklist item");
        System.out.println("2 -> View checklist");
        System.out.println("3 -> View packed items");
        System.out.println("4 -> View unpacked items");
        System.out.println("5 -> Mark packed (by index)");
        System.out.println("b -> Back");
        System.out.print("Choose: ");
    }

    private void doChecklistMenu() {
        boolean back = false;
        while (!back) {
            displayChecklistMenu();
            String command = input.nextLine().trim().toLowerCase();
            if (command.equals("1")) {
                addChecklistItem();
            } else if (command.equals("2")) {
                System.out.println(trip.getChecklist().getChecklistString());
            } else if (command.equals("3")) {
                System.out.println(trip.getChecklist().getPackedItems());
            } else if (command.equals("4")) {
                System.out.println(trip.getChecklist().getUnpackedItems());
            } else if (command.equals("5")) {
                markChecklistPacked();
            } else if (command.equals("b")) {
                back = true;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void addChecklistItem() {
        System.out.print("Item name: ");
        String name = input.nextLine();
        trip.getChecklist().addItem(new ChecklistItem(name));
        System.out.println("Item added.");
    }

    private void markChecklistPacked() {
        try {
            System.out.println(trip.getChecklist().getChecklistString());
            System.out.print("Enter index to mark packed(start with 1): ");
            int idx = Integer.parseInt(input.nextLine().trim()) - 1;

            // If your Checklist class doesn’t have markItemPacked yet, do it via the list:
            // trip.getChecklist().getChecklist().get(idx).markPacked();
            trip.getChecklist().getChecklist().get(idx).markPacked();

            if (trip.getChecklist().allPacked()) {
                System.out.println("All items packed!");
            } else {
                System.out.println("Marked packed.");
            }
        } catch (Exception e) {
            System.out.println("Invalid index.");
        }
    }

    // EFFECTS: saves the trip to file
    private void saveTrip() {
        try {
            jsonWriter.open();
            jsonWriter.write(trip);
            jsonWriter.close();
            System.out.println("Saved " + trip.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load the trip from file
    private void loadTrip() {
        try {
            trip = jsonReader.read();
            System.out.println("Loaded " + trip.getName());
        } catch (IOException e) {
            System.out.println("Unable to read from file.");
            trip = null;
        }
    }
}
