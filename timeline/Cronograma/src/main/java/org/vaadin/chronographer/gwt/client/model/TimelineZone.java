package org.vaadin.chronographer.gwt.client.model;

import java.util.Date;

public class TimelineZone {
    private Date start;
    private Date end;
    private Integer magnify;
    private Integer multiple;
    private TimeUnit unit;

    public TimelineZone() {

    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getMagnify() {
        return magnify;
    }

    public void setMagnify(Integer magnify) {
        this.magnify = magnify;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

}
