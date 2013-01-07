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

package org.vaadin.chronographer.gwt.client.ui;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.chronographer.gwt.client.netthreads.BandInfo;
import org.vaadin.chronographer.gwt.client.netthreads.BandOptions;
import org.vaadin.chronographer.gwt.client.netthreads.ClientSizeHelper;
import org.vaadin.chronographer.gwt.client.netthreads.EventSource;
import org.vaadin.chronographer.gwt.client.netthreads.HotZoneBandOptions;
import org.vaadin.chronographer.gwt.client.netthreads.PointHighlightDecorator;
import org.vaadin.chronographer.gwt.client.netthreads.PointHighlightDecoratorOptions;
import org.vaadin.chronographer.gwt.client.netthreads.SpanHighlightDecorator;
import org.vaadin.chronographer.gwt.client.netthreads.SpanHighlightDecoratorOptions;
import org.vaadin.chronographer.gwt.client.netthreads.Theme;
import org.vaadin.chronographer.gwt.client.netthreads.TimeLineClickHandler;
import org.vaadin.chronographer.gwt.client.netthreads.TimeLineWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VChronoGrapher extends TimeLineWidget implements Paintable,
        ResizeHandler {
    public static final String CLASSNAME = "timeline";
    private String uidlId;
    private ApplicationConnection client;
    private boolean inited = false;

    private final List<BandOptions> ourBandOptions = new ArrayList<BandOptions>();
    private final List<Theme> ourThemes = new ArrayList<Theme>();

    private int width;
    private int height;

    public VChronoGrapher() {
        super();
        setStyleName(CLASSNAME);
    }

    @Override
    public void updateFromUIDL(final UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            return;
        }
        this.client = client;
        uidlId = uidl.getId();

        if (!inited) {
            String widthStr = uidl.getStringAttribute("width");
            String heightStr = uidl.getStringAttribute("height");
            extractPixelWidthAndHeights(widthStr, heightStr);

            Window.addResizeHandler(this);
            setHorizontalOrientation(uidl.getBooleanAttribute("horizontal"));

            parseBandInfosAndZones(uidl);
            parseThemeJSON(uidl);

            initialise(width, height);
            getTimeLine().addClickHandler(new EventClickHandler());
            inited = true;
        }
        parseEventsJSON(uidl);
        onResize(true);
    }

    private void extractPixelWidthAndHeights(String widthStr, String heightStr) {
        width = ClientSizeHelper.getClientWidth();
        height = ClientSizeHelper.getClientHeight();

        if (widthStr.endsWith("px")) {
            width = Integer.parseInt(widthStr.substring(0,
                    widthStr.length() - 2).trim());
        } else if (widthStr.endsWith("%")) {
            int clientWidth = ClientSizeHelper.getClientWidth();
            int percentage = Integer.parseInt(widthStr.substring(0,
                    widthStr.length() - 1).trim());
            width = (int) (clientWidth * (percentage / 100.0));
        }

        if (heightStr.endsWith("px")) {
            height = Integer.parseInt(heightStr.substring(0,
                    heightStr.length() - 2).trim());
        } else if (heightStr.endsWith("%")) {
            int clientHeight = ClientSizeHelper.getClientHeight();
            int percentage = Integer.parseInt(heightStr.substring(0,
                    heightStr.length() - 1).trim());
            height = (int) (clientHeight * (percentage / 100.0));
        }
    }

    private void parseBandInfosAndZones(UIDL uidl) {
        UIDL bandInfoContent = uidl.getChildByTagName("infos");
        if (bandInfoContent != null) {
            for (int i = 0; i < bandInfoContent.getChildCount(); i++) {
                createBandInfoAndZones(bandInfoContent.getChildUIDL(i));
            }
        }
    }

    private void parseEventsJSON(UIDL uidl) {
        if (uidl.hasAttribute("jsonevents")) {
            getEventSource().loadJSON(uidl.getStringAttribute("jsonevents"));
        }
    }

    private void parseThemeJSON(UIDL uidl) {
        if (uidl.hasAttribute("theme")) {
            JSONArray themeObjArray = JSONParser.parseStrict(
                    uidl.getStringAttribute("theme")).isArray();

            for (int i = 0; i < themeObjArray.size(); i++) {
                JSONObject themeObj = themeObjArray.get(i).isObject();
                Theme theme = ourThemes.get(i);
                if (themeObj.containsKey("firstDayOfWeek")) {
                    int firstDayOfWeek = (int) themeObj.get("firstDayOfWeek")
                            .isNumber().doubleValue();
                    theme.setFirstDayOfWeek(firstDayOfWeek);
                }
                if (themeObj.containsKey("themesIndex")) {
                    int themesIndex = (int) themeObj.get("themesIndex")
                            .isNumber().doubleValue();
                    ourBandOptions.get(themesIndex).setTheme(theme);
                } else {
                    ourBandOptions.get(i).setTheme(theme);
                }

                JSONObject eventTheme = null;
                if (themeObj.containsKey("event")) {
                    eventTheme = themeObj.get("event").isObject();

                    JSONObject track = null;
                    JSONObject instant = null;
                    JSONObject duration = null;
                    JSONObject label = null;
                    JSONArray highlightColors = null;

                    if (eventTheme.containsKey("track")) {
                        track = eventTheme.get("track").isObject();
                    }
                    if (eventTheme.containsKey("instant")) {
                        instant = eventTheme.get("instant").isObject();
                    }
                    if (eventTheme.containsKey("duration")) {
                        duration = eventTheme.get("duration").isObject();
                    }
                    if (eventTheme.containsKey("label")) {
                        label = eventTheme.get("label").isObject();
                    }
                    if (eventTheme.containsKey("highlightColors")) {
                        highlightColors = eventTheme.get("highlightColors")
                                .isArray();
                    }

                    if (track != null) {
                        if (track.containsKey("gap")) {
                            theme.setEventTrackGap((float) track.get("gap")
                                    .isNumber().doubleValue());
                        }
                        if (track.containsKey("height")) {
                            theme.setEventTrackHeight((float) track
                                    .get("height").isNumber().doubleValue());
                        }
                        if (track.containsKey("offset")) {
                            theme.setEventTrackOffset((float) track
                                    .get("offset").isNumber().doubleValue());
                        }
                    }

                    if (instant != null) {
                        if (instant.containsKey("offset")) {
                            theme.setEventInstantIcon(instant.get("offset")
                                    .isString().stringValue());
                        }
                        if (instant.containsKey("impreciseColor")) {
                            theme.setEventInstantImpreciseColor(instant
                                    .get("impreciseColor").isString()
                                    .stringValue());
                        }
                        if (instant.containsKey("impreciseOpacity")) {
                            theme.setEventInstantImpreciseOpacity((int) instant
                                    .get("impreciseOpacity").isNumber()
                                    .doubleValue());
                        }
                        if (instant.containsKey("lineColor")) {
                            theme.setEventInstantLineColor(instant
                                    .get("lineColor").isString().stringValue());
                        }
                        if (instant.containsKey("showLineForNoText")) {
                            theme.setEventInstantShowLineForNoText(instant
                                    .get("showLineForNoText").isBoolean()
                                    .booleanValue());
                        }
                        if (instant.containsKey("icon")) {
                            theme.setEventInstantIcon(GWT.getModuleBaseURL()
                                    + instant.get("icon").isString()
                                            .stringValue());
                        }
                    }

                    if (duration != null) {
                        if (duration.containsKey("color")) {
                            theme.setEventDurationColor(duration.get("color")
                                    .isString().stringValue());
                        }
                        if (duration.containsKey("impreciseColor")) {
                            theme.setEventDurationImpreciseColor(duration
                                    .get("impreciseColor").isString()
                                    .stringValue());
                        }
                        if (duration.containsKey("impreciseOpacity")) {
                            theme.setEventDurationImpreciseOpacity((int) duration
                                    .get("impreciseOpacity").isNumber()
                                    .doubleValue());
                        }
                        if (duration.containsKey("opacity")) {
                            theme.setEventDurationOpacity((int) duration
                                    .get("opacity").isNumber().doubleValue());
                        }
                    }

                    if (label != null) {
                        if (label.containsKey("insideColor")) {
                            theme.setEventLabelInsideColor(label
                                    .get("insideColor").isString()
                                    .stringValue());
                        }
                        if (label.containsKey("outsideColor")) {
                            theme.setEventLabelOutsideColor(label
                                    .get("outsideColor").isString()
                                    .stringValue());
                        }
                        if (label.containsKey("width")) {
                            theme.setEventLabelWidth((int) label.get("width")
                                    .isNumber().doubleValue());
                        }
                    }
                }
                // theme.setEventHighlightColors(highlightColors.);
            }
        }
    }

    private void createBandInfoAndZones(UIDL childUIDL) {
        BandOptions options = createBandOptions(childUIDL);
        Integer syncWith = null;
        Boolean highligh = null;
        Boolean overview = null;

        // parse parameters for BandInfo-objects
        if (childUIDL.hasAttribute("syncWith")) {
            syncWith = childUIDL.getIntAttribute("syncWith");
        }
        if (childUIDL.hasAttribute("highligh")) {
            highligh = childUIDL.getBooleanAttribute("highligh");
        }
        if (childUIDL.hasAttribute("overview")) {
            overview = childUIDL.getBooleanAttribute("overview");
        }

        Theme theme = Theme.create();
        options.setTheme(theme);

        ourBandOptions.add(options);
        ourThemes.add(theme);

        List<HotZoneBandOptions> zones = getBandHotZones();

        List<Object> decorators = new ArrayList<Object>();
        for (int i = 0; i < childUIDL.getChildCount(); i++) {
            if (childUIDL.getChildUIDL(i).getTag().equals("z")) {
                zones.add(createZone(options, childUIDL.getChildUIDL(i)));
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
                    decoOpt.setTheme(theme);

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
                    decoOpt.setTheme(theme);

                    PointHighlightDecorator deco = PointHighlightDecorator
                            .create(decoOpt);
                    decorators.add(deco);
                }

            }
        }
        options.setZones(zones);

        // create hot zone
        BandInfo bandInfo = BandInfo.createHotZone(options);
        if (syncWith != null) {
            bandInfo.setSyncWith(syncWith);
        }
        if (highligh != null) {
            bandInfo.setHighlight(highligh);
        }
        if (overview != null) {
            bandInfo.setOverview(overview);
        }

        bandInfo.setDecorators(decorators);
        getBandInfos().add(bandInfo);
    }

    private HotZoneBandOptions createZone(BandOptions options, UIDL childUIDL) {
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

        if (eventSource == null) {
            eventSource = EventSource.create();
        }
        options.setEventSource(eventSource);

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
        if (childUIDL.hasAttribute("timeZone")) {
            Integer timeZone = childUIDL.getIntAttribute("timeZone");
            options.setTimeZone(timeZone);
        }

        return options;
    }

    /**
     * Resize all components
     */
    private void onResize(boolean create) {
        if (width <= 0) {
            width = ClientSizeHelper.getClientWidth();
        }
        if (height <= 0) {
            height = ClientSizeHelper.getClientHeight();
        }
        if (create || getTimeLine() == null) {
            setWidth(Integer.toString(width) + "px");
            setHeight(Integer.toString(height) + "px");
            create();
        }
        layout();
    }

    /**
     * Resize all components
     */
    @Override
    public void onResize(ResizeEvent event) {
        onResize(true);
    }

    class EventClickHandler implements TimeLineClickHandler {
        @Override
        public void onClick(int idd, int x, int y) {
            client.updateVariable(uidlId, "onclick",
                    new Object[] { idd, x, y }, true);
        }
    }
}
