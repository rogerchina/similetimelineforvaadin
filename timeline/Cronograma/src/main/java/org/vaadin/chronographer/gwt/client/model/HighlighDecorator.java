package org.vaadin.chronographer.gwt.client.model;

import java.util.Date;

import org.vaadin.chronographer.gwt.client.model.theme.TimelineTheme;

public class HighlighDecorator {
    private boolean isPointDecorator = false;
    private Date startDate;
    private Date endDate;
    private String startLabel;
    private String endLabel;
    private Date date;
    private String color;
    private Integer opacity;
    private TimelineTheme theme;

    public HighlighDecorator() {

    }

    private HighlighDecorator(Date startDate, Date endDate, String startLabel,
            String endLabel, Date date, String color, Integer opacity,
            TimelineTheme theme) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLabel = startLabel;
        this.endLabel = endLabel;
        this.date = date;
        this.color = color;
        this.opacity = opacity;
        this.theme = theme;
    }

    /**
     * Create highlight decorator for span (duration) events
     * 
     * @param startDate
     * @param endDate
     * @param startLabel
     * @param endLabel
     * @param color
     * @param opacity
     * @param theme
     * @return
     */
    public static HighlighDecorator createSpanDecorator(Date startDate,
            Date endDate, String startLabel, String endLabel, String color,
            Integer opacity, TimelineTheme theme) {
        HighlighDecorator deco = new HighlighDecorator(startDate, endDate,
                startLabel, endLabel, null, color, opacity, theme);
        deco.isPointDecorator = false;
        return deco;
    }

    /**
     * Create highlight decorator for point/instant events
     * 
     * @param date
     * @param color
     * @param opacity
     * @param theme
     * @return
     */
    public static HighlighDecorator createPointDecorator(Date date,
            String color, Integer opacity, TimelineTheme theme) {

        HighlighDecorator deco = new HighlighDecorator(null, null, null, null,
                date, color, opacity, theme);
        deco.isPointDecorator = true;
        return deco;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(String startLabel) {
        this.startLabel = startLabel;
    }

    public String getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(String endLabel) {
        this.endLabel = endLabel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOpacity() {
        return opacity;
    }

    public void setOpacity(Integer opacity) {
        this.opacity = opacity;
    }

    public TimelineTheme getTheme() {
        return theme;
    }

    public void setTheme(TimelineTheme theme) {
        this.theme = theme;
    }

    public boolean isPointDecorator() {
        return isPointDecorator;
    }

    public void setPointDecorator(boolean isPointDecorator) {
        this.isPointDecorator = isPointDecorator;
    }
}
