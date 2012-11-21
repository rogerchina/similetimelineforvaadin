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

package com.netthreads.gwt.simile.timeline.client;

public interface ITimeLineRender
{
	public void render(TimeLineWidget widget);
	/**
	 * Function to set Center data of all bands
	 * @param widget- timeline widget to set
	 * @param date- string representation of date. Tested so far with "YYYY Mon DD" format only
	 * 
	 * Note- in current implementation, this iterates the BandInfos that the widget holds, and calls the setDate function on each, passing
	 * in the string date..
	 */
	public void centerData(TimeLineWidget widget,String date);
}
