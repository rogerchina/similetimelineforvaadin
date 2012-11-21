package com.netthreads.test.simile.timeline.client.command;

import com.google.gwt.user.client.Command;
import com.netthreads.gwt.simile.timeline.client.TimeLineWidget;
import com.netthreads.gwt.simile.timeline.client.TimelineXMLHandler;


public class CommandLoadDataset implements Command
{
	private TimelineXMLHandler handler = null;
    private TimeLineWidget timeline = null;
    private String dataSet = "";

    public CommandLoadDataset(TimelineXMLHandler handler, TimeLineWidget timeline, String dataSet)
    {
        this.handler = handler;
        this.timeline = timeline;
        this.dataSet = dataSet;
    }

    public void execute()
    {
    	timeline.load(dataSet, handler);
    }
    
}
