package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestItineraryItem {

    private ItineraryItem item;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    @BeforeEach
    public void setup() {
        date = LocalDate.of(2026, 2, 12);
        start = LocalTime.of(9, 0);
        end = LocalTime.of(10, 0);
        item = new ItineraryItem(date, start, end, "Museum");
    }

    @Test
    public void testconstructorAndGetters() {
        assertEquals(date, item.getDate());
        assertEquals(start, item.getStartTime());
        assertEquals(end, item.getEndTime());
        assertEquals("Museum", item.getLocation());
        assertEquals(null, item.getNotes());
    }

    @Test
    public void testconflictsWithOverlap() {
        ItineraryItem other = new ItineraryItem(date,
                LocalTime.of(9, 30),
                LocalTime.of(11, 0),
                "Cafe");
        assertTrue(item.conflictsWith(other));
        assertTrue(other.conflictsWith(item));
    }

    @Test
    public void testconflictsWithTouchingEdge() {
        ItineraryItem other = new ItineraryItem(date,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                "Cafe");
        assertFalse(item.conflictsWith(other));
    }

    @Test
    public void testconflictsWithDifferentDate() {
        ItineraryItem other = new ItineraryItem(
                LocalDate.of(2026, 2, 13),
                LocalTime.of(9, 30),
                LocalTime.of(11, 0),
                "Cafe");
        assertFalse(item.conflictsWith(other));
    }

    @Test
    public void testsetStartTimeValid() {
        item.setStartTime(LocalTime.of(8, 30));
        assertEquals(LocalTime.of(8, 30), item.getStartTime());
    }

    @Test
    public void testsetStartTimeInvalid() {
        boolean exceptionThrown = false;

        try {
            item.setStartTime(LocalTime.of(10, 0));
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testsetEndTimeValid() {
        item.setEndTime(LocalTime.of(10, 30));
        assertEquals(LocalTime.of(10, 30), item.getEndTime());
    }

    @Test
    public void testsetEndTimeInvalid() {
        boolean exceptionThrown = false;

        try {
            item.setEndTime(LocalTime.of(8, 0));
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testgetDisplayItem() {
        item.setNotes("Buy tickets");
        String result = item.getDisplayItem();

        assertTrue(result.contains("2026-02-12"));
        assertTrue(result.contains("09:00-10:00"));
        assertTrue(result.contains("Museum"));
        assertTrue(result.contains("Buy tickets"));
    }
}
