package org.vaadin.addons.gridsterlayout.demo.basic;

import java.util.Map;

import org.vaadin.addons.gridsterlayout.client.GridsterWidgetPosition;

import com.vaadin.ui.Component;

public class ReportSet {

	private final String caption;
	private final Map<Component, GridsterWidgetPosition> positions;

	public ReportSet(final String caption, final Map<Component, GridsterWidgetPosition> positions) {
		super();
		this.caption = caption;
		this.positions = positions;
	}

	public String getCaption() {
		return caption;
	}

	public Map<Component, GridsterWidgetPosition> getPositions() {
		return positions;
	}

	@Override
	public String toString() {
		return caption;
	}

}
