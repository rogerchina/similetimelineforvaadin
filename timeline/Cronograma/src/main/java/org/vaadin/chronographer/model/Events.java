package org.vaadin.chronographer.model;

import java.util.ArrayList;
import java.util.List;

public class Events {
    private final List<TimelineEvent> events;

    public Events() {
        events = new ArrayList<TimelineEvent>();
    }

    public void add(TimelineEvent event) {
        events.add(event);
    }

    public void clear() {
        events.clear();
    }

    public List<TimelineEvent> getEvents() {
        return events;
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

}
