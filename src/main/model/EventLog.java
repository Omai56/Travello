package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of application events.
 * Uses the Singleton Design Pattern so there is only one EventLog.
 */
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    /**
     * EFFECTS: constructs an empty event log.
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * EFFECTS: returns the single instance of EventLog.
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds event e to the event log.
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * MODIFIES: this
     * EFFECTS: clears the event log and logs the event log cleared event.
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}