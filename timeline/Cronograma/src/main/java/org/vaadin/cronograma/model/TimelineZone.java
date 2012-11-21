package org.vaadin.cronograma.model;

import java.util.Date;

public class TimelineZone {
    private Date start;
    private Date end;
    private Number magnify;
    private Number multiple;
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

    public Number getMagnify() {
        return magnify;
    }

    public void setMagnify(Number magnify) {
        this.magnify = magnify;
    }

    public Number getMultiple() {
        return multiple;
    }

    public void setMultiple(Number multiple) {
        this.multiple = multiple;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

}
