package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTrip {
    private Trip trip;

    @BeforeEach
    public void setup() {
        trip = new Trip("Japan Trip");
    }

    @Test
    public void testConstructor() {
        assertEquals("Japan Trip", trip.getName());
        assertNotNull(trip.getItinerary());
        assertNotNull(trip.getExpenseLog());
        assertNotNull(trip.getChecklist());
    }

    @Test
    public void testSetName() {
        trip.setName("Vegas Trip");
        assertEquals("Vegas Trip", trip.getName());
    }
}
