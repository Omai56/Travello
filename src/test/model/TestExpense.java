package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestExpense {
    private Expense expense;
    private LocalDate date;

    @BeforeEach
    public void setup() {
        date = LocalDate.of(2026, 2, 12);
        expense = new Expense(date, 25.50, ExpenseCategory.FOOD, "Lunch");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(date, expense.getDate());
        assertEquals(25.50, expense.getAmount());
        assertEquals(ExpenseCategory.FOOD, expense.getCategory());
        assertEquals("Lunch", expense.getDescription());
    }

    @Test
    public void testConstructorInvalidAmount() {
        boolean exceptionThrown = false;
        try {
            new Expense(date, -1, ExpenseCategory.FOOD, "Invalid");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testSetAmountValid() {
        expense.setAmount(40.00);
        assertEquals(40.00, expense.getAmount());
    }

    @Test
    public void testSetAmountInvalid() {
        boolean exceptionThrown = false;
        try {
            expense.setAmount(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testSetCategory() {
        expense.setCategory(ExpenseCategory.TRANSPORT);
        assertEquals(ExpenseCategory.TRANSPORT, expense.getCategory());
    }

    @Test
    public void testSetDescription() {
        expense.setDescription("Taxi ride");
        assertEquals("Taxi ride", expense.getDescription());
    }

    @Test
    public void testSetDate() {
        LocalDate newDate = LocalDate.of(2026, 2, 15);
        expense.setDate(newDate);
        assertEquals(newDate, expense.getDate());
    }
}
