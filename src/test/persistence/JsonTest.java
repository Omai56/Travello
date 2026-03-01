package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkChecklistItem(String name, boolean packed, ChecklistItem item) {
        assertEquals(name, item.getName());
        assertEquals(packed, item.isPacked());
    }

    protected void checkExpense(double amount, ExpenseCategory category, Expense expense) {
        assertEquals(amount, expense.getAmount());
        assertEquals(category, expense.getCategory());
    }

    protected void checkTripBasic(String name, Trip trip) {
        assertEquals(name, trip.getName());
    }
}