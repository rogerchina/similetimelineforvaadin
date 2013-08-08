package org.vaadin.chronographer.gwt.client.shared;

import java.util.List;

import org.vaadin.chronographer.gwt.client.model.TimelineBandInfo;
import org.vaadin.chronographer.gwt.client.model.theme.TimelineTheme;

import com.vaadin.shared.AbstractComponentState;

public class ChronoGrapherState extends AbstractComponentState {

    public boolean horizontal = true;
    
    public boolean serverCallOnEventClickEnabled = false;

    public List<TimelineBandInfo> bandInfos;
    public List<TimelineTheme> timelineThemes;

    public String eventsJson;

    public boolean initialized;
}
