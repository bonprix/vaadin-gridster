package org.vaadin.addons.gridsterlayout.client;

import com.vaadin.shared.Connector;

public class GridsterWidget {

	private String id;
	private Connector connector;
	private GridsterWidgetPosition position;

	public GridsterWidget() {

	}

	public GridsterWidget(final String id, final Connector connector, final GridsterWidgetPosition position) {
		super();
		this.id = id;
		this.connector = connector;
		this.position = position;
	}

	public GridsterWidgetPosition getPosition() {
		return position;
	}

	public void setPosition(final GridsterWidgetPosition position) {
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public Connector getConnector() {
		return connector;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setConnector(final Connector connector) {
		this.connector = connector;
	}

}
