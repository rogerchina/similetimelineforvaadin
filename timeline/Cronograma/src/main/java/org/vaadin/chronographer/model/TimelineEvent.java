package org.vaadin.chronographer.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimelineEvent {
    private transient DateFormat df = new SimpleDateFormat(
            "EEE MMM dd yyyy HH:mm:ss Z", Locale.US);

    /** Basic attributes */
    private Integer id;
    private String start;
    private String end;
    private String title;
    private String description;
    private Boolean isDuration = false;

    /** Additional attributes */
    private String icon;
    private String image;
    private String link;
    private String tapeImage;
    private String tapeRepeat;
    private String caption;
    private String classname;
    private String color;
    private String textColor;

    public TimelineEvent() {

    }

    public Date getStart() throws ParseException {
        return getDateFormatter().parse(start);
    }

    public void setStart(Date start) {
        this.start = getDateFormatter().format(start);
    }

    public Date getEnd() throws ParseException {
        return getDateFormatter().parse(end);
    }

    public void setEnd(Date end) {
        this.end = getDateFormatter().format(end);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return description;
    }

    public void setBody(String body) {
        description = body;
    }

    public Boolean isDuration() {
        return isDuration;
    }

    public void setIsDuration(Boolean isDuration) {
        this.isDuration = isDuration;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTapeImage() {
        return tapeImage;
    }

    public void setTapeImage(String tapeImage) {
        this.tapeImage = tapeImage;
    }

    public String getTapeRepeat() {
        return tapeRepeat;
    }

    public void setTapeRepeat(String tapeRepeat) {
        this.tapeRepeat = tapeRepeat;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setDateFormatter(DateFormat df) {
        this.df = df;
    }

    public DateFormat getDateFormatter() {
        return df;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
