package org.vaadin.chronographer.gwt.client.model.theme.event;

public class EventsTheme {
    private Track track;
    private Instant instant;
    private Duration duration;
    private Label label;
    private String[] highlightColors;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String[] getHighlightColors() {
        return highlightColors;
    }

    public void setHighlightColors(String[] highlightColors) {
        this.highlightColors = highlightColors;
    }
}
