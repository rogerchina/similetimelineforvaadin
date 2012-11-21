package com.netthreads.test.simile.timeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.netthreads.gwt.simile.timeline.client.ClientSizeHelper;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class TimeLineTest implements EntryPoint, WindowResizeListener
{
    public static MainTabPanel mainPanel = null;

    public TimeLineTest()
    {
        mainPanel = new MainTabPanel();
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        // Add resize listner to pass brower window changes down to enclosed views
        Window.addWindowResizeListener(this);

        RootPanel.get().add(mainPanel);

        RootPanel.get("loadingtext").setVisible(false);

        // Load dataset 
        DatasetHandler.getInstance().initialise(this, getInitialDataset());

        // Firefox needs an initial resize 'bump'
        onWindowResized(ClientSizeHelper.getClientWidth(), ClientSizeHelper.getClientHeight());
    }

    /**
     * getInitialDataset 
     * 
     * <p>Returns dataset to load on startup. </p>
     * 
     * @return dataset name
     */
    private String getInitialDataset()
    {
        String dataset = "site/data/stonehenge.xml";

        /** 
         * Put your permalink code here to set the inital dataset from url params.
         * 
         */
        
        return (dataset);
    }
    
    /**
     * Resize all components
     */
    public void onWindowResized(int width, int height)
    {
        mainPanel.onWindowResized(ClientSizeHelper.getClientWidth(), ClientSizeHelper.getClientHeight());
    }

    public static MainTabPanel getMainPanel()
    {
        return mainPanel;
    }

    
}
