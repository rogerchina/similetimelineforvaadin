package org.vaadin.chronographer.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.vaadin.chronographer.ChronoGrapher;
import org.vaadin.chronographer.gwt.client.model.TimeUnit;
import org.vaadin.chronographer.gwt.client.model.TimelineBandInfo;
import org.vaadin.chronographer.gwt.client.model.TimelineEvent;
import org.vaadin.chronographer.gwt.client.model.TimelineZone;

import com.google.gwt.layout.client.Layout.Alignment;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MultipleTimelinesExample extends VerticalLayout {

	private static final long serialVersionUID = -2899916952402541583L;
	private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private int eventIdSequence = 0;

	private Component timeline1, timeline2, timeline3;

	public MultipleTimelinesExample() {
		setId("timeline-ex3-verticallayout");
	}

	@Override
	public void attach() {
		super.attach();
		try {
			com.vaadin.ui.Label infoLabel = new com.vaadin.ui.Label("Dave Developer");
			infoLabel.setId("timeline-ex3-infolabel");
			addComponent(infoLabel);
			addComponent(timeline1 = getNewTimelineComponent());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			com.vaadin.ui.Label infoLabel = new com.vaadin.ui.Label("Paula Project Manager");
			infoLabel.setId("timeline-ex2-infolabel");
			addComponent(infoLabel);
			addComponent(timeline2 = getNewTimelineComponent());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			com.vaadin.ui.Label infoLabel = new com.vaadin.ui.Label("Veronica Web Designer");
			infoLabel.setId("timeline-ex2-infolabel");
			addComponent(infoLabel);
			addComponent(timeline3 = getNewTimelineComponent());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		addComponentAsFirst(getEventAddLayout());
	}

	private Component getEventAddLayout() {
		final Label infoLabel = new Label("Create an event: ");
		final NativeSelect employeeSelect = new NativeSelect("Select employer", Arrays.asList(timeline1, timeline2, timeline3));
		employeeSelect.setItemCaption(timeline1, "Dave Developer");
		employeeSelect.setItemCaption(timeline2, "Paula Project Manager");
		employeeSelect.setItemCaption(timeline3, "Veronica Web Designer");
		employeeSelect.select(timeline1);
		employeeSelect.setNullSelectionAllowed(false);
		final TextField titleField = new TextField("Title");
		titleField.setValue("Test event");
		final DateField startDateField = new DateField("Starts");
		final DateField endDateField = new DateField("Ends");
		startDateField.setValue(new Date());
		endDateField.setValue(new Date());
		final ColorPicker colorPicker = new ColorPicker("Select event color");
		colorPicker.setColor(Color.GREEN);
		Button submitButton = new Button("Submit", new Button.ClickListener() {
			private static final long serialVersionUID = 9154868230465332746L;

			@Override
			public void buttonClick(ClickEvent event) {
				createAnEvent((ChronoGrapher) employeeSelect.getValue(), titleField.getValue(), startDateField.getValue(), endDateField.getValue(),
						colorPicker.getColor());
			}
		});

		HorizontalLayout layout = new HorizontalLayout(infoLabel,titleField, employeeSelect, startDateField, endDateField, colorPicker, submitButton);
		layout.setComponentAlignment(infoLabel, com.vaadin.ui.Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(titleField, com.vaadin.ui.Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(colorPicker, com.vaadin.ui.Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(submitButton, com.vaadin.ui.Alignment.BOTTOM_LEFT);
		layout.setSpacing(true);

		return layout;
	}

	private void createAnEvent(ChronoGrapher timeline, String title, Date startDate, Date endDate, Color color) {
		TimelineEvent event = new TimelineEvent();
		event.setIsDuration(true);
		event.setStart(startDate);
		event.setEnd(endDate);
		event.setTitle(title);
		event.setId(eventIdSequence++);
		event.setColor(color.getCSS());
		timeline.addEvent(event, true);
	}

	private Component getNewTimelineComponent() throws ParseException {
		ChronoGrapher timeline = new ChronoGrapher(UUID.randomUUID().toString(), true, false);
		timeline.setEventClickHandler(new ChronoGrapher.EventClickHandler() {
			@Override
			public void handleClick(TimelineEvent event) {
				Notification.show(String.format("Event with id #%s and title '%s' is clicked !", event.getId(), event.getTitle()));
			}
		});
		timeline.setImmediate(true);
		timeline.setWidth("100%");
		timeline.setHeight("100px");

		TimelineZone zone = new TimelineZone();
		zone.setStart(df.parse("2014-01-01"));
		zone.setEnd(df.parse("2014-12-31"));
		zone.setUnit(TimeUnit.WEEK);
		zone.setMultiple(1);
		zone.setMagnify(10);

		TimelineBandInfo bandInfo = new TimelineBandInfo();
		bandInfo.setDate(new Date());
		bandInfo.setTimeZone(+2);
		bandInfo.setWidth("100%");
		bandInfo.setIntervalUnit(TimeUnit.MONTH);
		bandInfo.setIntervalPixels(35);
		bandInfo.addTimelineZone(zone);

		timeline.addBandInfo(bandInfo);
		return timeline;
	}
}
