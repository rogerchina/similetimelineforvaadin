package com.netthreads.test.simile.timeline.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;

import com.netthreads.gwt.simile.timeline.client.ClientSizeHelper;
import com.netthreads.gwt.simile.timeline.client.TimeLineWidget;


/**
 * Main Panel
 *
 * Note: The Timeline view does not like any operation performed upon it unless it is the
 * focussed view. Not sure why not but you will save yourself endless grief if you remember
 * this.
 * 
 * Note: The client window sizes returned when each tab is selected are different, hence we 
 * have to force a resize to the correct view when each tab is selected.
 *
 */
public class MainTabPanel extends Composite
{
    private TabPanel panelTabbed = null;
    private AboutTab tabAbout = null;
    private TimeLineTab tabTimeLine = null;

    /**
     * MainTabPanel
     *
     */
    public MainTabPanel()
    {
        panelTabbed = new TabPanel();

        // Timeline
        tabTimeLine = new TimeLineTab();
        panelTabbed.add(tabTimeLine, "Timeline", false);

        // About
        tabAbout = new AboutTab();
        panelTabbed.add(tabAbout, "About", false);

        panelTabbed.selectTab(0);
        panelTabbed.setSize("100%", "100%");

        initWidget(panelTabbed);

        // Hook up a tab listener to do something when the user selects a tab.
        panelTabbed.addTabListener(new TabListener()
            {
            	TimeLineWidget widget = tabTimeLine.getTimeLineWidget();
            	
                public void onTabSelected(SourcesTabEvents sender, int tabIndex)
                {
                	resizeTabs(ClientSizeHelper.getClientWidth(), ClientSizeHelper.getClientHeight());
                }

                public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex)
                {
                    widget.clearBubbles();
                    return true;
                }
            });
        
        setStyleName("gwt-TabPanel");
    }

    /**
     * onWindowResized
     * @param width
     * @param height
     */
    public void onWindowResized(int width, int height)
    {
        resizeTabs(width, height);
    }

    /**
     * Resize view elements
     * 
     * @param width
     * @param height
     */
    private void resizeTabs(int width, int height)
    {
        if ((width > 0) && (height > 0))
        {
            int tabBarAbsoluteLeft = panelTabbed.getTabBar().getAbsoluteLeft();
            int tabBarOffsetHeight = panelTabbed.getTabBar().getOffsetHeight();

            int viewWidth = width - tabBarAbsoluteLeft;
            int viewHeight = height - tabBarOffsetHeight;

            tabTimeLine.onWindowResized(viewWidth, viewHeight);

            panelTabbed.setWidth(Integer.toString(width) + "px");
            panelTabbed.setHeight(Integer.toString(height) + "px");
        }
    }

    /**
     * Returns help tab
     *
     * @return map tab
     */
    public AboutTab getTabHelp()
    {
        return tabAbout;
    }

    /**
     * Returns timeline control
     *
     * @return map control
     */
    public TimeLineTab getTabTimeLine()
    {
        return tabTimeLine;
    }

    /**
     * Return tab
     * @return
     */
    public TabPanel getPanelTabbed()
    {
        return panelTabbed;
    }
}
