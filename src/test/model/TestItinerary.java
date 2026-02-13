package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestItinerary {
    private Itinerary itinerary;

    private LocalDate date;
    private ItineraryItem item1;
    private ItineraryItem item2;
    private ItineraryItem conflictItem;

    @BeforeEach
    public void setup() {
        itinerary = new Itinerary();

        date = LocalDate.of(2026, 2, 12);
        item1 = new ItineraryItem(date, LocalTime.of(9, 0), LocalTime.of(10, 0), "Museum");
        item2 = new ItineraryItem(date, LocalTime.of(10, 0), LocalTime.of(11, 0), "Lunch");
        conflictItem = new ItineraryItem(date, LocalTime.of(9, 30), LocalTime.of(10, 30), "Coffee");
    }

    @Test
    public void testConstructor() {
        assertEquals(itinerary.getItems().size(), 0);
        assertEquals("No itinerary items.", itinerary.printItinerary());

    }

    @Test
    public void testAddNonConflictingItem() {
        assertTrue(itinerary.addItem(item1));
        assertTrue(itinerary.addItem(item2));
        assertEquals(2, itinerary.getItems().size());
        assertEquals(item1, itinerary.getItems().get(0));
        assertEquals(item2, itinerary.getItems().get(1));
    }

    @Test
    public void testAddConflictingItem() {
        assertTrue(itinerary.addItem(item1));
        assertFalse(itinerary.addItem(conflictItem));
        assertEquals(1, itinerary.getItems().size());
        assertEquals(item1, itinerary.getItems().get(0));
    }

    @Test
    public void testHasConflict() {
        assertTrue(itinerary.addItem(item1));

        assertTrue(itinerary.hasConflict(conflictItem));
        assertFalse(itinerary.hasConflict(item2));
    }

    @Test
    public void testPrintItinerary() {
        itinerary.addItem(item1);
        itinerary.addItem(item2);

        String output = itinerary.printItinerary();
        assertTrue(output.contains("Museum"));
        assertTrue(output.contains("Lunch"));
        assertTrue(output.contains("2026-02-12"));
        assertTrue(output.contains("09:00-10:00"));
        assertTrue(output.contains("10:00-11:00"));
    }
}
