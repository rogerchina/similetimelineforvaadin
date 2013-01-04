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

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

/**
 * Cronograma demo application
 */
@SuppressWarnings("serial")
public class TimelineApplication extends com.vaadin.Application {
    private final Window main = new Window("Timeline Demo");
    private TabSheet tabsheet;
    private Label label;

    @Override
    public void init() {
        setMainWindow(main);
        main.setDebugId("timeline-mainwindow");

        createTabSheet();

        main.setSizeFull();
        main.setImmediate(true);
        main.getContent().setDebugId("timeline-mainwindow-content");
        main.addComponent(tabsheet);
    }

    private void createTabSheet() {
        tabsheet = new TabSheet();
        tabsheet.setDebugId("timeline-tabsheet");
        tabsheet.addTab(new SimpleTimelineExample(), "Simple example");
        tabsheet.addTab(new ComplexTimeLineExample(), "Complex example");
    }

}
