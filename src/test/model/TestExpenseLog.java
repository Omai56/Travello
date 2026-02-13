package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TestExpenseLog {
    private ExpenseLog log;
    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    public void setup() {
        log = new ExpenseLog();
        expense1 = new Expense(LocalDate.of(2026, 2, 12), 50.0,
                ExpenseCategory.FOOD, "Lunch");

        expense2 = new Expense(LocalDate.of(2026, 2, 13), 100.0,
                ExpenseCategory.TRANSPORT, "Taxi");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, log.getExpenses().size());
        assertEquals(0, log.totalSpent());
        assertEquals(0, log.getBudget());
    }

    @Test
    public void testAddExpense() {
        log.addExpense(expense1);
        assertEquals(1, log.getExpenses().size());
        assertEquals(50.0, log.totalSpent());
    }

    @Test
    public void testMultipleExpensesTotal() {
        log.addExpense(expense1);
        log.addExpense(expense2);
        assertEquals(2, log.getExpenses().size());
        assertEquals(150.0, log.totalSpent());
    }

    @Test
    public void testSetBudgetValid() {
        log.setBudget(200);
        assertEquals(200, log.getBudget());
    }

    @Test
    public void testSetBudgetInvalid() {
        boolean exceptionThrown = false;
        try {
            log.setBudget(-10);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testOverBudget() {
        log.setBudget(100);
        log.addExpense(expense1);
        log.addExpense(expense2);

        assertTrue(log.isOverBudget());
    }

    @Test
    public void testNearBudget() {
        log.setBudget(200);
        log.addExpense(expense1);
        log.addExpense(expense2);

        assertTrue(log.isNearBudget());
    }

    @Test
    public void testCategorySummary() {
        log.addExpense(expense1);
        log.addExpense(expense2);
        String summary = log.categorySummary();
        assertTrue(summary.contains("FOOD"));
        assertTrue(summary.contains("TRANSPORT"));
        assertTrue(summary.contains("50.0"));
        assertTrue(summary.contains("100.0"));
    }
}
