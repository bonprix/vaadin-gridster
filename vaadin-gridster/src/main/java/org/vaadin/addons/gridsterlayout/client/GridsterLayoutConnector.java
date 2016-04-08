package org.vaadin.addons.gridsterlayout.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.vaadin.addons.gridsterlayout.GridsterLayout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.annotations.JavaScript;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractLayoutConnector;
import com.vaadin.client.ui.customlayout.CustomLayoutConnector;
import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(GridsterLayout.class)
@JavaScript({ "jquery-1.11.3.min.js", "gridster.js" })
public class GridsterLayoutConnector extends AbstractLayoutConnector implements GridsterListener {

	GridsterLayoutServerRpc rpc = RpcProxy.create(GridsterLayoutServerRpc.class, this);

	private final List<Connector> attachedChildren = new ArrayList<Connector>();

	public GridsterLayoutConnector() {

	}

	@Override
	protected Widget createWidget() {
		final VGridsterLayout w = GWT.create(VGridsterLayout.class);

		w.setServerRpc(this);

		return w;
	}

	@Override
	public VGridsterLayout getWidget() {
		return (VGridsterLayout) super.getWidget();
	}

	@Override
	public GridsterLayoutState getState() {
		return (GridsterLayoutState) super.getState();
	}

	@Override
	public void onStateChanged(final StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
	}

	@Override
	public void updateCaption(final ComponentConnector paintable) {
		getWidget().updateCaption(paintable);
	}

	@SuppressWarnings("unused")
	private static Logger getLogger() {
		return Logger.getLogger(CustomLayoutConnector.class.getName());
	}

	@Override
	public void onConnectorHierarchyChange(final ConnectorHierarchyChangeEvent event) {
		getWidget().setConfig(getState().config);

		for (final ComponentConnector child : getChildComponents()) {
			if (attachedChildren.contains(child)) {
				continue;
			}

			final GridsterWidget gridsterWidget = getState().children.get(child);

			getWidget().addWidget(gridsterWidget.getId(), child.getWidget(), gridsterWidget.getPosition());
			attachedChildren.add(child);
		}
		for (final ComponentConnector oldChild : event.getOldChildren()) {
			if (oldChild.getParent() == this) {
				// Connector still a child of this
				continue;
			}
			final Widget oldChildWidget = oldChild.getWidget();
			if (oldChildWidget.isAttached()) {
				// slot of this widget is emptied, remove it
				getWidget().remove(oldChildWidget);
				attachedChildren.remove(oldChild);
			}
		}
	}

	private Map<String, GridsterWidgetPosition> getPositions() {
		final Map<String, GridsterWidgetPosition> positions = new HashMap<String, GridsterWidgetPosition>();

		for (final GridsterWidget w : getState().children.values()) {
			positions.put(w.getId(), getWidget().getPositions(w.getId()));
		}

		return positions;
	}

	@Override
	public void resized(final String componentId) {
		rpc.resized(componentId, getPositions());
	}

	@Override
	public void dragged(final String componentId) {
		rpc.dragged(componentId, getPositions());
	}

}
