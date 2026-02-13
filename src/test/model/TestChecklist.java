package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TestChecklist {
    private Checklist checklist;
    private ChecklistItem item1;
    private ChecklistItem item2;

    @BeforeEach
    public void setup() {
        checklist = new Checklist();
        item1 = new ChecklistItem("Passport");
        item2 = new ChecklistItem("Clothes");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, checklist.getChecklist().size());
        assertEquals("No checklist items.", checklist.getChecklistString());
    }

    @Test
    public void testAddItem() {
        checklist.addItem("Passport");
        checklist.addItem("Clothes");

        ArrayList<ChecklistItem> items = checklist.getChecklist();
        assertEquals(2, items.size());
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }

    @Test
    public void testAddItemWhenFull() {
        for (int i = 0; i < Checklist.MAX_ITEMS; i++) {
            checklist.addItem("Item" + i);
        }
        assertTrue(checklist.isFull());
        checklist.addItem("ExtraItem");
        assertEquals(Checklist.MAX_ITEMS, checklist.getChecklist().size());
    }

    @Test
    public void testAllPacked() {
        checklist.addItem("Passport");
        checklist.addItem("Clothes");
        ArrayList<ChecklistItem> items = checklist.getChecklist();
        assertFalse(checklist.allPacked());
        items.get(0).markPacked();
        items.get(1).markPacked();
        assertTrue(checklist.allPacked());
    }

    @Test
    public void testPackedAndUnpackedStrings() {
        checklist.addItem("Passport");
        checklist.addItem("Clothes");
        ArrayList<ChecklistItem> items = checklist.getChecklist();
        items.get(0).markPacked();
        assertTrue(checklist.getPackedItems().contains("Passport"));
        assertTrue(checklist.getUnpackedItems().contains("Clothes"));
    }

    @Test
    public void testIsFull() {
        assertFalse(checklist.isFull());
        for (int i = 0; i < Checklist.MAX_ITEMS; i++) {
            checklist.addItem("Item " + i);
        }
        assertTrue(checklist.isFull());
        assertEquals(Checklist.MAX_ITEMS, checklist.getChecklist().size());

    }
}
