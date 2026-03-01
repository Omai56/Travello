package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTrip() {
        try {
            Trip trip = new Trip("My Trip");

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTrip.json");
            writer.open();
            writer.write(trip);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTrip.json");
            trip = reader.read();

            assertEquals("My Trip", trip.getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTrip() {
        try {
            Trip trip = new Trip("Vacation");
            addGeneralTripData(trip);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTrip.json");
            writer.open();
            writer.write(trip);
            writer.close();

            Trip readTrip = new JsonReader("./data/testWriterGeneralTrip.json").read();
            checkGeneralTrip(readTrip);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void addGeneralTripData(Trip trip) {
        // checklist: one packed, one not packed
        ChecklistItem passport = new ChecklistItem("Passport");
        passport.markPacked();
        trip.getChecklist().addItem(passport);

        // expenses: one with description, one without
        trip.getExpenseLog().addExpense(new Expense(LocalDate.of(2025, 3, 1), 100.0,
                ExpenseCategory.FOOD, "Lunch"));
        trip.getExpenseLog().addExpense(new Expense(LocalDate.of(2025, 3, 2), 50.0,
                ExpenseCategory.TRANSPORT, null));

        // itinerary: one with notes, one without
        ItineraryItem i0 = new ItineraryItem(LocalDate.of(2025, 7, 1),
                LocalTime.of(9, 0), LocalTime.of(12, 0), "Beach");
        i0.setNotes("Bring sunscreen");

        ItineraryItem i1 = new ItineraryItem(LocalDate.of(2025, 7, 2),
                LocalTime.of(10, 0), LocalTime.of(13, 0), "Museum");

        trip.getItinerary().addItem(i0);
        trip.getItinerary().addItem(i1);
    }

    private void checkGeneralTrip(Trip trip) {
        assertEquals("Vacation", trip.getName());

        assertEquals(1, trip.getChecklist().getChecklist().size());
        checkChecklistItem("Passport", true, trip.getChecklist().getChecklist().get(0));

        assertEquals(2, trip.getExpenseLog().getExpenses().size());
        Expense e0 = trip.getExpenseLog().getExpenses().get(0);
        Expense e1 = trip.getExpenseLog().getExpenses().get(1);
        // if order is stable, check directly; otherwise you can “find by date”
        checkExpense(LocalDate.of(2025, 3, 1), 100.0, ExpenseCategory.FOOD, "Lunch", e0);
        checkExpense(LocalDate.of(2025, 3, 2), 50.0, ExpenseCategory.TRANSPORT, null, e1);

        assertEquals(2, trip.getItinerary().getItems().size());
        ItineraryItem i0 = trip.getItinerary().getItems().get(0);
        ItineraryItem i1 = trip.getItinerary().getItems().get(1);
        checkItineraryItem(LocalDate.of(2025, 7, 1),
                LocalTime.of(9, 0), LocalTime.of(12, 0),
                "Beach", "Bring sunscreen", i0);
        checkItineraryItem(LocalDate.of(2025, 7, 2),
                LocalTime.of(10, 0), LocalTime.of(13, 0),
                "Museum", null, i1);
    }
}