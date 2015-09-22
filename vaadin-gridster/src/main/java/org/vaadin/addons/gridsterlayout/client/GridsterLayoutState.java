package org.vaadin.addons.gridsterlayout.client;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.AbstractLayoutState;

public class GridsterLayoutState extends AbstractLayoutState {
	{
		primaryStyleName = "v-gridsterlayout";
	}

	public GridsterConfig config;

	public Map<Connector, GridsterWidget> children = new HashMap<Connector, GridsterWidget>();
}