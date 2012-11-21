/* 
 * Copyright 2009 IT Mill Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.cronograma;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.vaadin.cronograma.gwt.client.ui.VCronograma;
import org.vaadin.cronograma.model.Events;
import org.vaadin.cronograma.model.HighlighDecorator;
import org.vaadin.cronograma.model.TimelineBandInfo;
import org.vaadin.cronograma.model.TimelineEvent;
import org.vaadin.cronograma.model.TimelineZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ClientWidget;

@SuppressWarnings("serial")
@ClientWidget(VCronograma.class)
public class Cronograma extends AbstractField {
    private final transient DateFormat df = new SimpleDateFormat(
            "EEE MMM dd yyyy HH:mm:ss Z", Locale.US);

    private final List<TimelineBandInfo> bandInfos;

    private final Events timelineEvents;

    private boolean eventsChanged = false;
    private boolean structureChanged = false;

    public Cronograma() {
        super();
        bandInfos = new ArrayList<TimelineBandInfo>();
        timelineEvents = new Events();
    }

    public void addBandInfo(TimelineBandInfo bandInfo) {
        bandInfos.add(bandInfo);
        structureChanged = true;
    }

    public void addEvent(TimelineEvent event) {
        timelineEvents.add(event);
        eventsChanged = true;
    }

    public void addEvents(TimelineEvent... events) {
        for (TimelineEvent e : events) {
            timelineEvents.add(e);
        }
        eventsChanged = true;
        requestRepaint();
    }

    public void clearBandInfos() {
        bandInfos.clear();
        structureChanged = true;
    }

    public void clearEvents() {
        timelineEvents.clear();
        eventsChanged = true;
    }

    /** The property value of the field is a String. */
    @Override
    public Class<?> getType() {
        return String.class;
    }

    /** Paint (serialize) the component for the client. */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        System.out.println("Cronograma.paintContent");
        super.paintContent(target);
        if (structureChanged) {
            paintBandInfosAndZones(target);
            structureChanged = false;
            System.out.println("..structure");
        }
        if (eventsChanged) {
            paintEventsOnJSON(target);
            eventsChanged = false;
            System.out.println("...events");
        }
    }

    private void paintBandInfosAndZones(PaintTarget target)
            throws PaintException {
        target.startTag("infos");
        for (TimelineBandInfo info : bandInfos) {
            target.startTag("b");
            if (info.getWidth() != null) {
                target.addAttribute("width", info.getWidth());
            }
            if (info.getHighligh() != null) {
                target.addAttribute("highligh", info.getHighligh());
            }
            if (info.getOverview() != null) {
                target.addAttribute("overview", info.getOverview());
            }
            if (info.getIntervalPixels() != null) {
                target.addAttribute("intervalPixels", info.getIntervalPixels());
            }
            if (info.getTimeZone() != null) {
                target.addAttribute("timeZone", info.getTimeZone());
            }
            if (info.getIntervalUnit() != null) {
                target.addAttribute("intervalUnit", info.getIntervalUnit()
                        .ordinal());
            }
            if (info.getSyncWith() != null) {
                target.addAttribute("syncWith", info.getSyncWith());
            }
            if (info.getDate() != null) {
                target.addAttribute("date", df.format(info.getDate()));
            }
            if (info.getShowEventText() != null) {
                target.addAttribute("showEventText", info.getShowEventText());
            }
            if (info.getIntervalPixels() != null) {
                target.addAttribute("intervalPixels", info.getIntervalPixels()
                        .intValue());
            }
            if (info.getIntervalUnit() != null) {
                target.addAttribute("intervalUnit", info.getIntervalUnit()
                        .ordinal());
            }
            if (info.getTrackGap() != null) {
                target.addAttribute("trackGap", info.getTrackGap().intValue());
            }
            if (info.getTrackHeight() != null) {
                target.addAttribute("trackHeight", info.getTrackHeight()
                        .intValue());
            }
            for (TimelineZone zone : info.getTimelineZones()) {
                paintZone(target, zone);
            }
            for (HighlighDecorator decorator : info.getHighlightDecorators()) {
                paintDecorator(target, decorator);
            }
            target.endTag("b");
        }
        target.endTag("infos");
    }

    private void paintZone(PaintTarget target, TimelineZone zone)
            throws PaintException {
        target.startTag("z");
        if (zone.getStart() != null) {
            target.addAttribute("start", df.format(zone.getStart()));
        }
        if (zone.getEnd() != null) {
            target.addAttribute("end", df.format(zone.getEnd()));
        }
        if (zone.getMagnify() != null) {
            target.addAttribute("magnify", zone.getMagnify().intValue());
        }
        if (zone.getMultiple() != null) {
            target.addAttribute("multiple", zone.getMultiple().intValue());
        }
        if (zone.getUnit() != null) {
            target.addAttribute("unit", zone.getUnit().ordinal());
        }
        target.endTag("z");
    }

    private void paintDecorator(PaintTarget target, HighlighDecorator decorator)
            throws PaintException {
        target.startTag("d");
        if (decorator.getStartDate() != null) {
            target.addAttribute("startDate",
                    df.format(decorator.getStartDate()));
        }
        if (decorator.getEndDate() != null) {
            target.addAttribute("end", df.format(decorator.getEndDate()));
        }
        if (decorator.getStartLabel() != null) {
            target.addAttribute("startLabel", decorator.getStartLabel());
        }
        if (decorator.getEndLabel() != null) {
            target.addAttribute("endLabel", decorator.getEndLabel());
        }
        if (decorator.getDate() != null) {
            target.addAttribute("date", df.format(decorator.getDate()));
        }
        if (decorator.getColor() != null) {
            target.addAttribute("color", decorator.getColor());
        }
        if (decorator.getOpacity() != null) {
            target.addAttribute("opacity", decorator.getOpacity());
        }
        // if (decorator.getUnit() != null) {
        // target.addAttribute("date", decorator.getUnit().ordinal());
        // }
        target.endTag("d");

    }

    private void paintEventsOnJSON(PaintTarget target) throws PaintException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        System.out.println(gson.toJson(timelineEvents));
        target.addAttribute("jsonevents", gson.toJson(timelineEvents));
    }

    /** Deserialize changes received from client. */
    @SuppressWarnings("unchecked")
    @Override
    public void changeVariables(Object source, Map variables) {
        if (variables.containsKey("onclick")) {
            Object[] params = (Object[]) variables.get("onclick");
            getWindow().showNotification(
                    "Event " + params[0] + " clicked @ (" + params[1] + ","
                            + params[2] + ")");
        }
    }
}
