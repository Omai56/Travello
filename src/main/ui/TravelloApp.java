package ui;

import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TravelloApp {

    private Scanner input;
    private Trip trip;

    public TravelloApp() {
        input = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to Travello!");

        System.out.print("Enter trip name: ");
        String name = input.nextLine();
        trip = new Trip(name);

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
            } else {
                System.out.println("Invalid option.");
            }
        }
        System.out.println("Thank you for using Travello!");
    }

    private void displayMainMenu() {
        System.out.println("\n=== Main Menu (" + trip.getName() + ") ===");
        System.out.println("i -> Itinerary");
        System.out.println("e -> Expenses");
        System.out.println("c -> Checklist");
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
        trip.getChecklist().addItem(name);
        System.out.println("Item added.");
    }

    private void markChecklistPacked() {
        try {
            System.out.println(trip.getChecklist().getChecklistString());
            System.out.print("Enter index to mark packed: ");
            int idx = Integer.parseInt(input.nextLine().trim());

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
}
