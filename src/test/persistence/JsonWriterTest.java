package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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

            trip.getChecklist().addItem("Passport");

            trip.getExpenseLog().addExpense(
                    new Expense(java.time.LocalDate.now(), 100,
                            ExpenseCategory.FOOD, "Lunch"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTrip.json");
            writer.open();
            writer.write(trip);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTrip.json");
            trip = reader.read();

            assertEquals("Vacation", trip.getName());
            assertEquals(1, trip.getChecklist().getChecklist().size());
            assertEquals(1, trip.getExpenseLog().getExpenses().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}