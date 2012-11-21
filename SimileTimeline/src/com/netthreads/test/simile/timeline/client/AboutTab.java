/*
 * Copyright 2006 Alistair Rutherford (http://code.google.com/p/gwtsimiletimeline/)
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

package com.netthreads.test.simile.timeline.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * A composite that holds html frame
 */
public class AboutTab extends Composite
{
    // GUI elements
	private VerticalPanel panel = new VerticalPanel();

    /**
     * Constructor
     */
    public AboutTab()
    {
		panel.add(makeLabel("About this site"));
		panel.add(makeFrame("help/about.html"));

		panel.add(makeLabel("Credits"));
		panel.add(makeFrame("help/credits.html"));
		
		initWidget(panel);
		
		setStyleName("app-Info-Panel");
		panel.setWidth("100%");
		panel.setHeight("100%");
	}


    /**
     * Make title label
     * 
     * @param caption for label
     * @return created label
     */
    private HTML makeLabel(String caption)
    {
        HTML html = new HTML(caption);
        html.setStyleName("app-Info-Label");

        return html;
    }

    /**
     * Make frame for panel. Note workaround code to supess frame border and
     * scrollbar.
     * 
     * @param url of target html
     * @return created Frame
     */
    private Frame makeFrame(String url)
    {
        Frame frame = new Frame(url);
        frame.setStyleName("app-Info-Frame");
        DOM.setElementPropertyInt(frame.getElement(), "frameBorder", 0); // disable border
        DOM.setElementProperty(frame.getElement(), "scrolling", "no"); // disable scroll
        
        return frame;
    }
    
}
