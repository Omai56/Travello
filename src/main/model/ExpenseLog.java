package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a log of expenses for a trip.
 * Stores multiple Expense objects and tracks total spending and budget status.
 */
public class ExpenseLog {

    private List<Expense> expenses;
    private double budget;

    // EFFECTS: constructs an empty expense log with budget = 0 (no budget set).
    public ExpenseLog() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds expense to this expense log.
    public void addExpense(Expense expense) {
        // stub
    }

    // EFFECTS: returns the list of expenses in this expense log.
    public List<Expense> getExpenses() {
        return null; // stub
    }

    // EFFECTS: returns the total amount spent across all expenses
    // in this expense log.
    public double totalSpent() {
        return 0.0; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets the budget of this expense log to budget.
    // throws IllegalArgumentException if budget < 0.
    public void setBudget(double budget) {
        // stub
    }

    // EFFECTS: returns the budget of this expense log.
    public double getBudget() {
        return 0.0;
    }

    // EFFECTS: returns true if budget > 0 and totalSpent() > budget;
    // returns false otherwise.
    public boolean isOverBudget() {
        return false;
    }

    // EFFECTS: returns true if budget > 0 and totalSpent() >= 0.9 * budget;
    // returns false otherwise.
    public boolean isNearBudget() {
        return false;
    }

    // EFFECTS: returns a formatted string containing all expenses
    // in this expense log.
    // If there are no expenses, returns "No expenses."
    public String printExpenses() {
        return null;
    }

    // EFFECTS: returns a formatted string summarizing total spending
    // grouped by ExpenseCategory.
    // Each category appears with its total spending.
    // If there are no expenses, returns "No expenses."
    public String categorySummary() {
        return null;
    }
}
