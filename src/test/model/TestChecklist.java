package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TestChecklist {
    private Checklist checklist;

    @BeforeEach
    public void setup() {
        checklist = new Checklist();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, checklist.getChecklist().size());
        assertEquals("No checklist items.", checklist.getChecklistString());
    }

    @Test
    public void testAddItem() {
        checklist.addItem(new ChecklistItem("Passport"));
        checklist.addItem(new ChecklistItem("Clothes"));

        ArrayList<ChecklistItem> items = checklist.getChecklist();
        assertEquals(2, items.size());
        assertEquals("Passport", items.get(0).getName());
        assertEquals("Clothes", items.get(1).getName());
    }

    @Test
    public void testAddItemWhenFull() {
        for (int i = 0; i < Checklist.MAX_ITEMS; i++) {
            checklist.addItem(new ChecklistItem("Item" + i));
        }
        assertTrue(checklist.isFull());
        checklist.addItem(new ChecklistItem("ExtraItem"));
        assertEquals(Checklist.MAX_ITEMS, checklist.getChecklist().size());
    }

    @Test
    public void testAllPacked() {
        assertFalse(checklist.allPacked());
        checklist.addItem(new ChecklistItem("Passport"));
        checklist.addItem(new ChecklistItem("Clothes"));
        ArrayList<ChecklistItem> items = checklist.getChecklist();
        assertFalse(checklist.allPacked());
        items.get(0).markPacked();
        items.get(1).markPacked();
        assertTrue(checklist.allPacked());
    }

    @Test
    public void testPackedAndUnpackedStrings() {
        checklist.addItem(new ChecklistItem("Passport"));
        checklist.addItem(new ChecklistItem("Clothes"));
        ArrayList<ChecklistItem> items = checklist.getChecklist();
        items.get(0).markPacked();
        assertTrue(checklist.getPackedItems().contains("Passport"));
        assertTrue(checklist.getUnpackedItems().contains("Clothes"));
    }

    @Test
    public void testIsFull() {
        assertFalse(checklist.isFull());
        for (int i = 0; i < Checklist.MAX_ITEMS; i++) {
            checklist.addItem(new ChecklistItem("Item " + i));
        }
        assertTrue(checklist.isFull());
        assertEquals(Checklist.MAX_ITEMS, checklist.getChecklist().size());

    }

    @Test
    public void testgetChecklistString() {
        assertEquals("No checklist items.", checklist.getChecklistString());
        checklist.addItem(new ChecklistItem("Passport"));
        checklist.addItem(new ChecklistItem("Clothes"));

        String output = checklist.getChecklistString();
        assertTrue(output.contains("Passport"));
        assertTrue(output.contains("Clothes"));
    }
}
