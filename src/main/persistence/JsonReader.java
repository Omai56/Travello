package persistence;

import model.Trip;
import model.ChecklistItem;
import model.Expense;
import model.ExpenseCategory;
import model.ItineraryItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads trip from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads trip from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Trip read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrip(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses trip from JSON object and returns it
    private Trip parseTrip(JSONObject jsonObject) {
        String name = jsonObject.getString("name");

        Trip trip = new Trip(name);

        addItinerary(trip, jsonObject.getJSONObject("itinerary"));
        addExpenseLog(trip, jsonObject.getJSONObject("expenseLog"));
        addChecklist(trip, jsonObject.getJSONObject("checklist"));

        return trip;
    }

    // MODIFIES: trip
    // EFFECTS: parses itinerary from JSON object and adds them to trip
    private void addItinerary(Trip trip, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");

        for (Object obj : jsonArray) {
            JSONObject itemObj = (JSONObject) obj;
            LocalDate date = LocalDate.parse(itemObj.getString("date"));
            LocalTime start = LocalTime.parse(itemObj.getString("startTime"));
            LocalTime end = LocalTime.parse(itemObj.getString("endTime"));
            String location = itemObj.getString("location");
            ItineraryItem item = new ItineraryItem(date, start, end, location);

            if (itemObj.has("notes")) {
                item.setNotes(itemObj.getString("notes"));
            }
            trip.getItinerary().addItem(item);
        }
    }

    // MODIFIES: trip
    // EFFECTS: parses expense log from JSON object and adds them to trip
    private void addExpenseLog(Trip trip, JSONObject jsonObject) {
        double budget = jsonObject.getDouble("budget");
        trip.getExpenseLog().setBudget(budget);

        JSONArray jsonArray = jsonObject.getJSONArray("expenses");

        for (Object obj : jsonArray) {
            JSONObject expenseObj = (JSONObject) obj;

            LocalDate date = LocalDate.parse(expenseObj.getString("date"));
            double amount = expenseObj.getDouble("amount");
            ExpenseCategory category = ExpenseCategory.valueOf(expenseObj.getString("category"));

            String description = null;
            if (expenseObj.has("description")) {
                description = expenseObj.getString("description");
            }

            Expense expense = new Expense(date, amount, category, description);
            trip.getExpenseLog().addExpense(expense);
        }
    }

    // MODIFIES: trip
    // EFFECTS: parses checklist from JSON object and adds them to trip
    private void addChecklist(Trip trip, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");

        for (Object obj : jsonArray) {
            JSONObject itemObj = (JSONObject) obj;

            String name = itemObj.getString("name");
            ChecklistItem item = new ChecklistItem(name);

            if (itemObj.getBoolean("packed")) {
                item.markPacked();
            }

            trip.getChecklist().addItem(name);
        }
    }

}
