package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Trip trip = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTrip() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTrip.json");
        try {
            Trip trip = reader.read();
            assertEquals("My Trip", trip.getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTrip() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTrip.json");
        try {
            Trip trip = reader.read();

            assertEquals("Vacation", trip.getName());
            assertEquals(2, trip.getChecklist().getChecklist().size());
            assertEquals(2, trip.getExpenseLog().getExpenses().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}