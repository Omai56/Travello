package persistence;

import model.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkChecklistItem(String expectedName, boolean expectedPacked, ChecklistItem item) {
        assertEquals(expectedName, item.getName());
        assertEquals(expectedPacked, item.isPacked());
    }

    protected void checkExpense(LocalDate expectedDate, double expectedAmount,
            ExpenseCategory expectedCategory, String expectedDescription,
            Expense expense) {
        assertEquals(expectedDate, expense.getDate());
        assertEquals(expectedAmount, expense.getAmount());
        assertEquals(expectedCategory, expense.getCategory());
        assertEquals(expectedDescription, expense.getDescription());
    }

    protected void checkItineraryItem(LocalDate expectedDate, LocalTime expectedStart, LocalTime expectedEnd,
            String expectedLocation, String expectedNotes,
            ItineraryItem item) {
        assertEquals(expectedDate, item.getDate());
        assertEquals(expectedStart, item.getStartTime());
        assertEquals(expectedEnd, item.getEndTime());
        assertEquals(expectedLocation, item.getLocation());
        assertEquals(expectedNotes, item.getNotes());
    }
}