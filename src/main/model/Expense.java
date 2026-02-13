package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single expense made during a trip.
 * Each expense has a date, a positive amount, a category, and an optional
 * description.
 */
public class Expense {
    // EFFECTS: constructs an expense with the given date, amount, category, and
    // description.
    // throws IllegalArgumentException if amount <= 0.
    public Expense(LocalDate date, double amount,
            ExpenseCategory category, String description) {
        // stub
    }

    // EFFECTS: returns the date of this expense.
    public LocalDate getDate() {
        return null; // stub
    }

    // EFFECTS: returns the amount of this expense.
    public double getAmount() {
        return 0.0;
    }

    // EFFECTS: returns the category of this expense.
    public ExpenseCategory getCategory() {
        return null;
    }

    // EFFECTS: returns the description of this expense; may be null.
    public String getDescription() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the amount of this expense to amount.
    // throws IllegalArgumentException if amount <= 0.
    public void setAmount(double amount) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets the date of this expense to date.
    public void setDate(LocalDate date) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets the category of this expense to category.
    public void setCategory(ExpenseCategory category) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets the description of this expense to description (may be null).
    public void setDescription(String description) {
        // stub
    }
}
