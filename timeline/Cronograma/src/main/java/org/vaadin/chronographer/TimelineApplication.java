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

package org.vaadin.chronographer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.vaadin.chronographer.model.HighlighDecorator;
import org.vaadin.chronographer.model.TimeUnit;
import org.vaadin.chronographer.model.TimelineBandInfo;
import org.vaadin.chronographer.model.TimelineEvent;
import org.vaadin.chronographer.model.TimelineTheme;
import org.vaadin.chronographer.model.TimelineZone;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;

/**
 * Cronograma demo application
 */
@SuppressWarnings("serial")
public class TimelineApplication extends com.vaadin.Application {
    private final Window main = new Window("Timeline Demo");
    private final ChronoGrapher timeline = new ChronoGrapher();
    private final DateFormat df2 = new SimpleDateFormat(
            "EEE MMM dd yyyy HH:mm:ss", Locale.US);
    private final DateFormat df3 = new SimpleDateFormat("MMM dd yyyy HH:mm:ss",
            Locale.US);

    @Override
    public void init() {
        setMainWindow(main);
        main.setSizeFull();
        main.setImmediate(true);
        try {
            addBandInfosAndZonesIntoTimeline();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        main.addComponent(new Button("Load Events", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    addEventsIntoTimeline();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        }));

        main.addComponent(timeline);
        timeline.setImmediate(true);
        timeline.setWidth("100%");
        timeline.setHeight("500px");

        timeline.addListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                System.out.println("valueChange");
            }
        });
    }

    private void addBandInfosAndZonesIntoTimeline() throws ParseException {
        TimelineTheme theme = new TimelineTheme();
        theme.setEventLabelWidth(400);

        TimelineBandInfo bandInfo1 = new TimelineBandInfo();
        TimelineBandInfo bandInfo2 = new TimelineBandInfo();

        HighlighDecorator deco1 = HighlighDecorator.createSpanDecorator(
                df2.parse("Fri Nov 22 1963 12:30:00"),
                df2.parse("Fri Nov 22 1963 13:00:00"), "shot", "t.o.d.",
                "#33C080", 50, theme);
        HighlighDecorator deco2 = HighlighDecorator.createPointDecorator(
                df2.parse("Fri Nov 22 1963 14:38:00"), "#33C080", 50, theme);
        HighlighDecorator deco3 = HighlighDecorator.createPointDecorator(
                df2.parse("Sun Nov 24 1963 13:00:00 "), "#33C080", 50, theme);

        TimelineZone zone11 = new TimelineZone();
        TimelineZone zone12 = new TimelineZone();
        TimelineZone zone13 = new TimelineZone();
        TimelineZone zone14 = new TimelineZone();

        TimelineZone zone21 = new TimelineZone();
        TimelineZone zone22 = new TimelineZone();
        TimelineZone zone23 = new TimelineZone();
        TimelineZone zone24 = new TimelineZone();

        zone11.setStart(df2.parse("Fri Nov 22 1963 00:00:00"));
        zone11.setEnd(df2.parse("Mon Nov 25 1963 00:00:00"));
        zone11.setMagnify(10);
        zone11.setUnit(TimeUnit.DAY);

        zone12.setStart(df2.parse("Fri Nov 22 1963 09:00:00"));
        zone12.setEnd(df2.parse("Sun Nov 24 1963 00:00:00"));
        zone12.setMagnify(5);
        zone12.setUnit(TimeUnit.HOUR);

        zone13.setStart(df2.parse("Fri Nov 22 1963 11:00:00"));
        zone13.setEnd(df2.parse("Sat Nov 23 1963 00:00:00"));
        zone13.setMagnify(5);
        zone13.setUnit(TimeUnit.MINUTE);
        zone13.setMultiple(10);

        zone14.setStart(df2.parse("Fri Nov 22 1963 12:00:00"));
        zone14.setEnd(df2.parse("Fri Nov 22 1963 14:00:00"));
        zone14.setMagnify(3);
        zone14.setUnit(TimeUnit.MINUTE);
        zone14.setMultiple(5);

        zone21.setStart(df2.parse("Fri Nov 22 1963 00:00:00"));
        zone21.setEnd(df2.parse("Mon Nov 25 1963 00:00:00"));
        zone21.setMagnify(10);
        zone21.setUnit(TimeUnit.WEEK);

        zone22.setStart(df2.parse("Fri Nov 22 1963 09:00:00"));
        zone22.setEnd(df2.parse("Sun Nov 24 1963 00:00:00"));
        zone22.setMagnify(5);
        zone22.setUnit(TimeUnit.DAY);

        zone23.setStart(df2.parse("Fri Nov 22 1963 11:00:00"));
        zone23.setEnd(df2.parse("Sat Nov 23 1963 00:00:00"));
        zone23.setMagnify(5);
        zone23.setUnit(TimeUnit.MINUTE);
        zone23.setMultiple(60);

        zone24.setStart(df2.parse("Fri Nov 22 1963 12:00:00"));
        zone24.setEnd(df2.parse("Fri Nov 22 1963 14:00:00"));
        zone24.setMagnify(3);
        zone24.setUnit(TimeUnit.MINUTE);
        zone24.setMultiple(15);

        bandInfo1.addTimelineZone(zone11);
        bandInfo1.addTimelineZone(zone12);
        bandInfo1.addTimelineZone(zone13);
        bandInfo1.addTimelineZone(zone14);
        bandInfo1.addHighlightDecorator(deco1);
        bandInfo1.addHighlightDecorator(deco2);
        bandInfo1.addHighlightDecorator(deco3);

        bandInfo2.addTimelineZone(zone21);
        bandInfo2.addTimelineZone(zone22);
        bandInfo2.addTimelineZone(zone23);
        bandInfo2.addTimelineZone(zone24);
        bandInfo2.addHighlightDecorator(deco1);
        bandInfo2.addHighlightDecorator(deco2);
        bandInfo2.addHighlightDecorator(deco3);

        bandInfo1.setDate(df2.parse("Fri Nov 22 1963 13:00:00"));
        bandInfo1.setTimeZone(-6);
        bandInfo1.setWidth("80%");
        bandInfo1.setIntervalUnit(TimeUnit.WEEK);
        bandInfo1.setIntervalPixels(220);
        bandInfo1.setTheme(theme);

        bandInfo2.setDate(df2.parse("Fri Nov 22 1963 13:00:00"));
        bandInfo2.setTimeZone(-6);
        bandInfo2.setWidth("20%");
        bandInfo2.setIntervalUnit(TimeUnit.MONTH);
        bandInfo2.setIntervalPixels(200);
        bandInfo2.setOverview(true);
        bandInfo2.setTheme(theme);
        bandInfo2.setSyncWith(0);
        bandInfo2.setHighligh(true);

        timeline.addBandInfo(bandInfo1);
        timeline.addBandInfo(bandInfo2);
    }

    private void addEventsIntoTimeline() throws DocumentException {
        List<TimelineEvent> events = new ArrayList<TimelineEvent>();
        SAXReader reader = new SAXReader();
        Document document = reader.read("jfk.xml");
        Element root = document.getRootElement();
        int index = 0;

        // iterate through child elements of root
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element element = (Element) i.next();

            String start = element.attributeValue("start");
            String end = element.attributeValue("end");
            String title = element.attributeValue("title");
            String content = element.getText();

            start = start.substring(0, start.length() - 9);

            TimelineEvent event = new TimelineEvent();
            try {
                event.setStart(df2.parse(start));
                if (end != null) {
                    end = end.substring(0, end.length() - 9);
                    event.setEnd(df2.parse(end));
                    event.setIsDuration(true);
                }
            } catch (ParseException e) {
                try {
                    event.setStart(df3.parse(start));
                    if (end != null) {
                        end = end.substring(0, end.length() - 9);
                        event.setEnd(df3.parse(end));
                        event.setIsDuration(true);
                    }
                } catch (ParseException e1) {
                    System.out.println("unparseable date - skipped");
                    continue;
                }
            }
            event.setTitle(title);
            event.setBody(content);
            event.setId(index);
            events.add(event);
            ++index;
        }
        timeline.addEvents(events);
    }
}
