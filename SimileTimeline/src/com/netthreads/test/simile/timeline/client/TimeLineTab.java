package com.netthreads.test.simile.timeline.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;

import com.netthreads.gwt.simile.timeline.client.TimeLineWidget;
import com.netthreads.gwt.simile.timeline.client.ITimeLineRender;

/**
 * A composite that holds SIMILE Timeline control
 */
public class TimeLineTab extends Composite
{
    // GUI elements
    private ScrollPanel panel = null;
    private TimeLineWidget simileWidget = null;

    /**
     * Constructor
     */
    public TimeLineTab()
    {
        panel = new ScrollPanel();

        ITimeLineRender render = new StonehengeRender();
        simileWidget = new TimeLineWidget();
        render.render(simileWidget);
        panel.add(simileWidget);
        
        panel.setAlwaysShowScrollBars(false);

        initWidget(panel);

        // Apply default style
        simileWidget.setStyleName("timeline-default");
    }
    
    /**
     * onWindowResized
     * @param width
     * @param height
     */
    public void onWindowResized(int width, int height)
    {
        resize(width, height);
    }

    /**
     * Resize view elements
     * 
     * @param width
     * @param height
     */
    private void resize(int width, int height)
    {
        if ((width > 0) && (height > 0))
        {
        	
            panel.setWidth(Integer.toString(width) + "px");
            panel.setHeight(Integer.toString(height) + "px");
            
            simileWidget.setWidth(Integer.toString(width) + "px");
            simileWidget.setHeight(Integer.toString(height) + "px");
            
            simileWidget.layout();
        }
        
    }

    /**
     * Returns timeline widget to other UI components.
     * 
     * @return widget reference.
     */
    public TimeLineWidget getTimeLineWidget()
    {
        return simileWidget;
    }
    
}
