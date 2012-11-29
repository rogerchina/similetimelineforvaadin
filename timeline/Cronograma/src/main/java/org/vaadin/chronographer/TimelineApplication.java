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
    private final DateFormat df = new SimpleDateFormat("yyyy,MM,dd,HH:mm");;

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
                } catch (ParseException e) {
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
                df.parse("1963,11,22,12:30"), df.parse("1963,11,22,13:00"),
                "shot", "t.o.d.", "#33C080", 50, theme);
        HighlighDecorator deco2 = HighlighDecorator.createPointDecorator(
                df.parse("1963,11,22,14:38"), "#33C080", 50, theme);
        HighlighDecorator deco3 = HighlighDecorator.createPointDecorator(
                df.parse("1963,11,24,13:00"), "#33C080", 50, theme);

        TimelineZone zone11 = new TimelineZone();
        TimelineZone zone12 = new TimelineZone();
        TimelineZone zone13 = new TimelineZone();
        TimelineZone zone14 = new TimelineZone();

        TimelineZone zone21 = new TimelineZone();
        TimelineZone zone22 = new TimelineZone();
        TimelineZone zone23 = new TimelineZone();
        TimelineZone zone24 = new TimelineZone();

        zone11.setStart(df.parse("1963,11,22,00:00"));
        zone11.setEnd(df.parse("1963,11,25,00:00"));
        zone11.setMagnify(10);
        zone11.setUnit(TimeUnit.DAY);

        zone12.setStart(df.parse("1963,11,22,00:00"));
        zone12.setEnd(df.parse("1963,11,24,00:00"));
        zone12.setMagnify(5);
        zone12.setUnit(TimeUnit.HOUR);

        zone13.setStart(df.parse("1963,11,22,11:00"));
        zone13.setEnd(df.parse("1963,11,23,00:00"));
        zone13.setMagnify(5);
        zone13.setUnit(TimeUnit.MINUTE);
        zone13.setMultiple(10);

        zone14.setStart(df.parse("1963,11,22,12:00"));
        zone14.setEnd(df.parse("1963,11,22,14:00"));
        zone14.setMagnify(3);
        zone14.setUnit(TimeUnit.MINUTE);
        zone14.setMultiple(5);

        zone21.setStart(df.parse("1963,11,22,00:00"));
        zone21.setEnd(df.parse("1963,11,25,00:00"));
        zone21.setMagnify(10);
        zone21.setUnit(TimeUnit.WEEK);

        zone22.setStart(df.parse("1963,11,22,00:00"));
        zone22.setEnd(df.parse("1963,11,24,00:00"));
        zone22.setMagnify(5);
        zone22.setUnit(TimeUnit.DAY);

        zone23.setStart(df.parse("1963,11,22,11:00"));
        zone23.setEnd(df.parse("1963,11,23,00:00"));
        zone23.setMagnify(5);
        zone23.setUnit(TimeUnit.MINUTE);
        zone23.setMultiple(60);

        zone24.setStart(df.parse("1963,11,22,12:00"));
        zone24.setEnd(df.parse("1963,11,22,14:00"));
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

        bandInfo1.setDate(df.parse("1963,11,22,13:00"));
        bandInfo1.setTimeZone(-6);
        bandInfo1.setWidth("80%");
        bandInfo1.setIntervalUnit(TimeUnit.WEEK);
        bandInfo1.setIntervalPixels(200);
        bandInfo1.setTheme(theme);

        bandInfo2.setDate(df.parse("1963,11,22,13:00"));
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

    private void addEventsIntoTimeline() throws ParseException {
        String[] eventStarts = { "1961,05,20,00:00", "1963,05,01,00:00",
                "1963,05,10,00:00", "1963,06,05,00:00", "1963,10,20,00:00",
                "1963,11,22,11:00", "1963,11,22,11:10", "1963,11,22,11:30" };
        String[] eventEnds = { null, "1963,06,01,00:00", null, null, null,
                null, null, null };
        String[] eventTitles = { "'Bay of Pigs' Invasion",
                "Oswald moves to New Orleans", "General Walker shot",
                "Kennedy, Johnson, Connelly met",
                "Maurice Bishop seen speaking to Oswald",
                "Man with rifle spotted on Elm St.",
                "Air Force One leaves Fort Worth",
                "Motorcade leaves Lovefield Airport" };
        String[] eventContents = {
                null,
                "Oswald moves to New Orleans, and finds employment at the William B. Riley Coffee Company. &lt;i&gt;ref. Treachery in Dallas, p 320&lt;/i&gt;",
                "General Walker shot at in his home. &lt;i&gt;ref. Treachery in Dallas, p 319&lt;/i&gt;",
                "Decision for Texas trip made at meeting with Kennedy, Johnson, and Connelly at the Cortez hotel in El Paso Texas. &lt;i&gt;ref. W.C.&lt;i&gt;",
                "Antonio Veciana travels to Dallas for a meeting with Maurice Bishop (a.k.a. David Atlee Philips). In the lobby of the Southland building, Veciana sees Bishop speaking to a man Veciana later identifies as Lee Harvey Oswald. &lt;i&gt;ref. Last Investigation, p 141&lt;/i&gt;",
                "...", "...", ".." };

        TimelineEvent[] events = new TimelineEvent[8];
        for (int i = 0; i < 8; i++) {
            TimelineEvent event = new TimelineEvent();
            event.setStart(df.parse(eventStarts[i]));
            if (eventEnds[i] != null) {
                event.setEnd(df.parse(eventEnds[i]));
                event.setIsDuration(true);
            }
            event.setTitle(eventTitles[i]);
            event.setBody(eventContents[i]);
            event.setId(i);
            events[i] = event;
        }
        timeline.addEvents(events);
        System.out.println("requestRepaint");
    }
}
