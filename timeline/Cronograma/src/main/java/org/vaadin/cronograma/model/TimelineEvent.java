package org.vaadin.cronograma.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimelineEvent {
    private final transient DateFormat df = new SimpleDateFormat(
            "EEE MMM dd yyyy HH:mm:ss Z", Locale.US);

    private Integer id;
    private String start;
    private String end;
    private String title;
    private String description;
    private Boolean isDuration = false;
    private String image;
    private String link;

    public TimelineEvent() {

    }

    public Date getStart() throws ParseException {
        return df.parse(start);
    }

    public void setStart(Date start) {
        this.start = df.format(start);
    }

    public Date getEnd() throws ParseException {
        return df.parse(end);
    }

    public void setEnd(Date end) {
        this.end = df.format(end);
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
}
