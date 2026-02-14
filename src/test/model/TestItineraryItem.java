package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class TestItineraryItem {

    private ItineraryItem item;

    @BeforeEach
    public void setup() {
        item = new ItineraryItem(
                LocalDate.of(2026, 2, 12),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                "Museum");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(LocalDate.of(2026, 2, 12), item.getDate());
        assertEquals(LocalTime.of(9, 0), item.getStartTime());
        assertEquals(LocalTime.of(10, 0), item.getEndTime());
        assertEquals("Museum", item.getLocation());
        assertNull(item.getNotes());
    }

    @Test
    public void testConstructorInvalidTimes() {
        boolean thrown = false;
        try {
            new ItineraryItem(
                    LocalDate.of(2026, 2, 12),
                    LocalTime.of(10, 0),
                    LocalTime.of(10, 0),
                    "Lunch");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testConflictsDifferentDateFalse() {
        ItineraryItem other = new ItineraryItem(
                LocalDate.of(2026, 2, 13),
                LocalTime.of(9, 30),
                LocalTime.of(10, 30),
                "Shop");
        assertFalse(item.conflictsWith(other));
    }

    @Test
    public void testConflictsOverlapTrue() {
        ItineraryItem other = new ItineraryItem(
                LocalDate.of(2026, 2, 12),
                LocalTime.of(9, 30),
                LocalTime.of(10, 30),
                "Shop");
        assertTrue(item.conflictsWith(other));
    }

    @Test
    public void testConflictsTouchingFalse() {
        ItineraryItem other = new ItineraryItem(
                LocalDate.of(2026, 2, 12),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "Lunch");
        ItineraryItem other2 = new ItineraryItem(
                LocalDate.of(2026, 2, 12),
                LocalTime.of(8, 0),
                LocalTime.of(9, 0),
                "Lunch");
        assertFalse(item.conflictsWith(other));
        assertFalse(item.conflictsWith(other2));
    }

    @Test
    public void testSettersValid() {
        item.setDate(LocalDate.of(2026, 2, 20));
        item.setLocation("Aquarium");
        item.setNotes("Bring camera");
        item.setStartTime(LocalTime.of(8, 0));
        item.setEndTime(LocalTime.of(9, 0));

        assertEquals(LocalDate.of(2026, 2, 20), item.getDate());
        assertEquals("Aquarium", item.getLocation());
        assertEquals("Bring camera", item.getNotes());
        assertEquals(LocalTime.of(8, 0), item.getStartTime());
        assertEquals(LocalTime.of(9, 0), item.getEndTime());
    }

    @Test
    public void testSetStartTimeInvalidThrows() {
        boolean thrown = false;
        try {
            item.setStartTime(LocalTime.of(10, 0));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testSetEndTimeInvalidThrows() {
        boolean thrown = false;
        try {
            item.setEndTime(LocalTime.of(9, 0));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testGetDisplayItemNotesNull() {
        String s = item.getDisplayItem();
        assertTrue(s.contains("Date: 2026-02-12"));
        assertTrue(s.contains("Time: 09:00-10:00"));
        assertTrue(s.contains("Location: Museum"));
        assertFalse(s.contains("Notes:"));
    }

    @Test
    public void testGetDisplayItemNotesIncluded() {
        item.setNotes("Bring water");
        String s = item.getDisplayItem();
        assertTrue(s.contains("Notes: Bring water"));
    }
}
