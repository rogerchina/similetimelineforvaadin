package org.vaadin.chronographer.theme;

import org.vaadin.chronographer.theme.ether.EthersTheme;
import org.vaadin.chronographer.theme.event.EventsTheme;

public class TimelineTheme {
    private Integer themesIndex;
    private Integer firstDayOfWeek;
    private EthersTheme ether;
    private EventsTheme event;

    public Integer getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(Integer firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public EthersTheme getEther() {
        return ether;
    }

    public void setEther(EthersTheme ether) {
        this.ether = ether;
    }

    public EventsTheme getEvent() {
        return event;
    }

    public void setEvent(EventsTheme event) {
        this.event = event;
    }

    /**
     * Set index of Timeline Band for which this theme is intended
     * 
     * @param index
     */
    public void setThemesIndex(Integer themesIndex) {
        this.themesIndex = themesIndex;
    }

    public Integer getThemesIndex() {
        return themesIndex;
    }
}
