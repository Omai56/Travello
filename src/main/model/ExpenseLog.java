package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a log of expenses for a trip.
 * Stores multiple Expense objects and tracks total spending and budget status.
 */
public class ExpenseLog implements Writable {

    private List<Expense> expenses;
    private double budget;

    // EFFECTS: constructs an empty expense log with budget = 0 (no budget set).
    public ExpenseLog() {
        expenses = new ArrayList<>();
        budget = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds expense to this expense log.
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    // EFFECTS: returns the list of expenses in this expense log.
    public List<Expense> getExpenses() {
        return expenses;
    }

    // EFFECTS: returns the total amount spent across all expenses
    // in this expense log.
    public double totalSpent() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    // MODIFIES: this
    // EFFECTS: sets the budget of this expense log to budget.
    // throws IllegalArgumentException if budget < 0.
    public void setBudget(double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("Budget must be >= 0");
        }
        this.budget = budget;
    }

    // EFFECTS: returns the budget of this expense log.
    public double getBudget() {
        return budget;
    }

    // EFFECTS: returns true if budget > 0 and totalSpent() > budget;
    // returns false otherwise.
    public boolean isOverBudget() {
        Boolean overBudget = budget > 0 && totalSpent() > budget;
        return overBudget;
    }

    // EFFECTS: returns true if budget > 0 and totalSpent() >= 90% of budget;
    // returns false otherwise.
    public boolean isNearBudget() {
        return budget > 0 && totalSpent() >= 0.9 * budget;
    }

    // EFFECTS: return the remaining budget by budget - total spent
    public double getRemainingBudget() {
        return budget - totalSpent();
    }

    // EFFECTS: returns a formatted string containing all expenses
    // in this expense log.
    // If there are no expenses, returns "No expenses."
    public String printExpenses() {
        if (expenses.size() == 0) {
            return "No expenses.";
        }
        String result = "";
        for (Expense e : expenses) {
            result += "Date: " + e.getDate() + "\n"
                    + "Category: " + e.getCategory() + "\n"
                    + "Amount: " + e.getAmount() + "\n"
                    + "Description: " + e.getDescription() + "\n";
        }
        return result;
    }

    // EFFECTS: returns a formatted string summarizing total spending
    // grouped by ExpenseCategory.
    // Each category appears with its total spending.
    // If there are no expenses, returns "No expenses."
    public String categorySummary() {
        if (expenses.size() == 0) {
            return "No expenses.";
        }
        String result = "";
        for (ExpenseCategory c : ExpenseCategory.values()) {
            double total = 0;

            for (Expense e : expenses) {
                if (e.getCategory() == c) {
                    total += e.getAmount();
                }
            }
            result += c + ": " + total + "\n";
        }
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("budget", budget);
        json.put("expenses", expensesToJson());
        return json;
    }

    // EFFECTS: returns expenses in this ExpenseLog as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : expenses) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
