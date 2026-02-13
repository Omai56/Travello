package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTrip {
    private Trip trip;
    private Itinerary itinerary;
    private ExpenseLog expenseLog;
    private Checklist checklist;

    @BeforeEach
    public void setup() {
        trip = new Trip("Japan Trip");
        itinerary = new Itinerary();
        expenseLog = new ExpenseLog();
        checklist = new Checklist();
    }

    @Test
    public void testConstructor() {
        assertEquals("Japan Trip", trip.getName());
        assertEquals(itinerary, trip.getItinerary());
        assertEquals(expenseLog, trip.getExpenseLog());
        assertEquals(checklist, trip.getChecklist());
    }

    @Test
    public void testSetName() {
        trip.setName("Vegas Trip");
        assertEquals("Vegas Trip", trip.getName());
    }
}
