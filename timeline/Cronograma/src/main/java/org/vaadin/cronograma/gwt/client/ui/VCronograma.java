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

package org.vaadin.cronograma.gwt.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.netthreads.gwt.simile.timeline.client.BandInfo;
import com.netthreads.gwt.simile.timeline.client.BandOptions;
import com.netthreads.gwt.simile.timeline.client.ClientSizeHelper;
import com.netthreads.gwt.simile.timeline.client.EventSource;
import com.netthreads.gwt.simile.timeline.client.HotZoneBandOptions;
import com.netthreads.gwt.simile.timeline.client.PointHighlightDecorator;
import com.netthreads.gwt.simile.timeline.client.PointHighlightDecoratorOptions;
import com.netthreads.gwt.simile.timeline.client.SpanHighlightDecorator;
import com.netthreads.gwt.simile.timeline.client.SpanHighlightDecoratorOptions;
import com.netthreads.gwt.simile.timeline.client.Theme;
import com.netthreads.gwt.simile.timeline.client.TimeLineClickHandler;
import com.netthreads.gwt.simile.timeline.client.TimeLineWidget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.VConsole;

public class VCronograma extends TimeLineWidget implements Paintable,
        WindowResizeListener {

    public static final String CLASSNAME = "invoic-timeline";
    String uidlId;
    ApplicationConnection client;
    private boolean inited = false;

    public VCronograma() {
        super();
        setStyleName(CLASSNAME);
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            return;
        }
        this.client = client;
        uidlId = uidl.getId();

        if (!inited) {
            Window.addWindowResizeListener(this);
            parseBandInfosAndZones(uidl);
            initialise(getElement().getClientWidth(),
                    ClientSizeHelper.getClientHeight() - 100);

            getTimeLine().addClickHandler(new EventClickHandler());

            inited = true;
        }

        parseEventsJSON(uidl);
        onWindowResized(ClientSizeHelper.getClientWidth(),
                ClientSizeHelper.getClientHeight());
    }

    /**
     * Resize all components
     */
    @Override
    public void onWindowResized(int width, int height) {
        setWidth(Integer.toString(width) + "px");
        setHeight(Integer.toString(height) + "px");
        initialise(width, height - 100);
        layout();
    }

    private void parseDecorators() {
        // createBandDecorators();
    }

    private void parseBandInfosAndZones(UIDL uidl) {
        UIDL zones = uidl.getChildUIDL(0);
        if (zones != null) {
            for (int i = 0; i < zones.getChildCount(); i++) {
                createBandInfoAndZones(zones.getChildUIDL(i));
            }
            parseDecorators();
        }
    }

    private void parseEventsJSON(UIDL uidl) {
        if (uidl.hasAttribute("jsonevents")) {
            VConsole.log(uidl.getStringAttribute("jsonevents"));
            getEventSource().loadJSON(uidl.getStringAttribute("jsonevents"));
        }

    }

    private void createBandInfoAndZones(UIDL childUIDL) {
        BandOptions options = createBandOptions(childUIDL);
        BandInfo bandInfo = BandInfo.create(options);
        getBandInfos().add(bandInfo);

        if (childUIDL.hasAttribute("syncWith")) {
            Integer syncWith = childUIDL.getIntAttribute("syncWith");
            bandInfo.setSyncWith(syncWith);
        }
        if (childUIDL.hasAttribute("highligh")) {
            Boolean highligh = childUIDL.getBooleanAttribute("highligh");
            bandInfo.setHighlight(highligh);
        }

        // Boolean overview = childUIDL.getBooleanAttribute("overview");

        List<HotZoneBandOptions> zones = getBandHotZones();
        List<Object> decorators = new ArrayList<Object>();
        for (int i = 0; i < childUIDL.getChildCount(); i++) {
            if (childUIDL.getChildUIDL(i).getTag().equals("z")) {
                zones.add(createZone(bandInfo, options,
                        childUIDL.getChildUIDL(i)));
            } else if (childUIDL.getChildUIDL(i).getTag().equals("d")) {
                UIDL decoUIDL = childUIDL.getChildUIDL(i);
                if (decoUIDL.hasAttribute("startDate")) {
                    SpanHighlightDecoratorOptions decoOpt = SpanHighlightDecoratorOptions
                            .create();
                    decoOpt.setStartDate(decoUIDL
                            .getStringAttribute("startDate"));
                    if (decoUIDL.hasAttribute("endDate")) {
                        decoOpt.setEndDate(decoUIDL
                                .getStringAttribute("endDate"));
                    }
                    if (decoUIDL.hasAttribute("startLabel")) {
                        decoOpt.setStartLabel(decoUIDL
                                .getStringAttribute("startLabel"));
                    }
                    if (decoUIDL.hasAttribute("endLabel")) {
                        decoOpt.setEndLabel(decoUIDL
                                .getStringAttribute("endLabel"));
                    }
                    if (decoUIDL.hasAttribute("color")) {
                        decoOpt.setColor(decoUIDL.getStringAttribute("color"));
                    }
                    if (decoUIDL.hasAttribute("opacity")) {
                        decoOpt.setOpacity(decoUIDL.getIntAttribute("opacity"));
                    }
                    // if (childUIDL.hasAttribute("theme")) {
                    // decoOpt.setEndDate(childUIDL.getStringAttribute(""));
                    // }
                    decoOpt.setTheme(getTheme());

                    SpanHighlightDecorator deco = SpanHighlightDecorator
                            .create(decoOpt);
                    decorators.add(deco);
                } else if (decoUIDL.hasAttribute("date")) {
                    PointHighlightDecoratorOptions decoOpt = PointHighlightDecoratorOptions
                            .create();
                    decoOpt.setDate(decoUIDL.getStringAttribute("date"));
                    if (decoUIDL.hasAttribute("color")) {
                        decoOpt.setColor(decoUIDL.getStringAttribute("color"));
                    }
                    if (decoUIDL.hasAttribute("opacity")) {
                        decoOpt.setOpacity(decoUIDL.getIntAttribute("opacity"));
                    }

                    // if (childUIDL.hasAttribute("theme")) {
                    //
                    // }
                    decoOpt.setTheme(getTheme());

                    PointHighlightDecorator deco = PointHighlightDecorator
                            .create(decoOpt);
                    decorators.add(deco);
                }

            }
        }
        options.setZones(zones);
        bandInfo.setDecorators(decorators);
    }

    private HotZoneBandOptions createZone(BandInfo bandInfo,
            BandOptions options, UIDL childUIDL) {
        HotZoneBandOptions zone = HotZoneBandOptions.create();

        if (childUIDL.hasAttribute("start")) {
            String start = childUIDL.getStringAttribute("start");
            zone.setStart(start);
        }
        if (childUIDL.hasAttribute("end")) {
            String end = childUIDL.getStringAttribute("end");
            zone.setEnd(end);
        }
        if (childUIDL.hasAttribute("magnify")) {
            Integer magnify = childUIDL.getIntAttribute("magnify");
            zone.setMagnify(magnify);
        }
        if (childUIDL.hasAttribute("multiple")) {
            Integer multiple = childUIDL.getIntAttribute("multiple");
            zone.setMultiple(multiple);
        }
        if (childUIDL.hasAttribute("unit")) {
            Integer unit = childUIDL.getIntAttribute("unit");
            zone.setUnit(unit);
        }

        return zone;
    }

    private BandOptions createBandOptions(UIDL childUIDL) {
        BandOptions options = BandOptions.create();
        EventSource eventSource = getEventSource();
        Theme theme = getTheme();
        if (eventSource == null) {
            eventSource = EventSource.create();
        }
        if (theme == null) {
            theme = Theme.create();
        }
        options.setEventSource(eventSource);
        options.setTheme(theme);

        if (childUIDL.hasAttribute("width")) {
            String width = childUIDL.getStringAttribute("width");
            options.setWidth(width);
        }
        if (childUIDL.hasAttribute("date")) {
            String date = childUIDL.getStringAttribute("date");
            options.setDate(date);
        }
        if (childUIDL.hasAttribute("trackGap")) {
            Integer trackGap = childUIDL.getIntAttribute("trackGap");
            options.setTrackGap(trackGap);
        }
        if (childUIDL.hasAttribute("trackHeight")) {
            Integer trackHeight = childUIDL.getIntAttribute("trackHeight");
            options.setTrackHeight(trackHeight);
        }
        if (childUIDL.hasAttribute("intervalPixels")) {
            Integer intervalPixels = childUIDL
                    .getIntAttribute("intervalPixels");
            options.setIntervalPixels(intervalPixels);
        }
        if (childUIDL.hasAttribute("intervalUnit")) {
            Integer intervalUnit = childUIDL.getIntAttribute("intervalUnit");
            options.setIntervalUnit(intervalUnit);
        }
        if (childUIDL.hasAttribute("showEventText")) {
            Boolean showEventText = childUIDL
                    .getBooleanAttribute("showEventText");
            options.setShowEventText(showEventText);
        }

        return options;
    }

    class EventClickHandler implements TimeLineClickHandler {
        @Override
        public void onClick(int id, int x, int y, String description) {
            client.updateVariable(uidlId, "onclick", new Object[] { id, x, y },
                    true);
        }
    };
}
