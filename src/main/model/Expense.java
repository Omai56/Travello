package model;

import java.time.LocalDate;

/**
 * Represents a single expense made during a trip.
 * Each expense has a date, a positive amount, a category, and an optional
 * description.
 */
public class Expense {
    private LocalDate date;
    private double amount;
    private ExpenseCategory category;
    private String description;

    // EFFECTS: constructs an expense with the given date, amount, category, and
    // description.
    // throws IllegalArgumentException if amount <= 0.
    public Expense(LocalDate date, double amount,
            ExpenseCategory category, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // EFFECTS: returns the date of this expense.
    public LocalDate getDate() {
        return date;
    }

    // EFFECTS: returns the amount of this expense.
    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns the category of this expense.
    public ExpenseCategory getCategory() {
        return category;
    }

    // EFFECTS: returns the description of this expense; may be null.
    public String getDescription() {
        return description;
    }

    // MODIFIES: this
    // EFFECTS: sets the amount of this expense to amount.
    // throws IllegalArgumentException if amount <= 0.
    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: sets the date of this expense to date.
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // MODIFIES: this
    // EFFECTS: sets the category of this expense to category.
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    // MODIFIES: this
    // EFFECTS: sets the description of this expense to description (may be null).
    public void setDescription(String description) {
        this.description = description;
    }
}
