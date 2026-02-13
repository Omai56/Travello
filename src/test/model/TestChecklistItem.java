package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestChecklistItem {

    private ChecklistItem item;

    @BeforeEach
    public void setup() {
        item = new ChecklistItem("Passport");
    }

    @Test
    public void testConstructor() {
        assertEquals("Passport", item.getName());
        assertFalse(item.isPacked());
    }

    @Test
    public void testGetName() {
        assertEquals("Passport", item.getName());
    }

    @Test
    public void testIsPacked() {
        assertFalse(item.isPacked());
        item.markPacked();
        assertTrue(item.isPacked());
    }

    @Test
    public void testMarkPacked() {
        assertFalse(item.isPacked());
        item.markPacked();
        assertTrue(item.isPacked());
    }

    @Test
    public void testMarkUnpacked() {
        item.markPacked();
        assertTrue(item.isPacked());
        item.markUnpacked();
        assertFalse(item.isPacked());
    }

    @Test
    public void testGetChecklistItem() {
        assertEquals("[ ] Passport", item.getChecklistItem());
        item.markPacked();
        assertEquals("[X] Passport", item.getChecklistItem());
        item.markPacked();
        assertEquals("[X] Passport", item.getChecklistItem());
    }

}
