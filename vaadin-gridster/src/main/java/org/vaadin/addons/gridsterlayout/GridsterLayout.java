package org.vaadin.addons.gridsterlayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.vaadin.addons.gridsterlayout.ComponentResizeListener.ComponentResizeEvent;
import org.vaadin.addons.gridsterlayout.StateChangeListener.StateChangeEvent;
import org.vaadin.addons.gridsterlayout.client.GridsterConfig;
import org.vaadin.addons.gridsterlayout.client.GridsterLayoutServerRpc;
import org.vaadin.addons.gridsterlayout.client.GridsterLayoutState;
import org.vaadin.addons.gridsterlayout.client.GridsterWidget;
import org.vaadin.addons.gridsterlayout.client.GridsterWidgetPosition;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.Connector;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;

@StyleSheet("vaadin://gridster/jquery.gridster.css")
@JavaScript({ "vaadin://gridster/jquery-1.11.3.min.js", "vaadin://gridster/jquery.collision.js", "vaadin://gridster/jquery.coords.js",
	"vaadin://gridster/jquery.draggable.js", "vaadin://gridster/jquery.gridster.with-extras.js", "vaadin://gridster/utils.js" })
public class GridsterLayout extends AbstractLayout {

	private final Map<String, GridsterWidget> children = new HashMap<String, GridsterWidget>();
	private final Map<Component, String> connectorToId = new HashMap<Component, String>();

	// To process events from the client, we implement ServerRpc
	private final GridsterLayoutServerRpc rpc = new GridsterLayoutServerRpc() {

		@Override
		public void dragged(final String id, final Map<String, GridsterWidgetPosition> state) {
			dragged0(id, state);
		}

		@Override
		public void resized(final String id, final Map<String, GridsterWidgetPosition> state) {
			resized0(id, state);
		}

	};

	public GridsterLayout() {
		this(new GridsterConfig());
	}

	public GridsterLayout(final GridsterConfig config) {
		registerRpc(rpc);

		getState().config = config;
	}

	// We must override getState() to cast the state to MyComponentState
	@Override
	public GridsterLayoutState getState() {
		return (GridsterLayoutState) super.getState();
	}

	@Override
	public void replaceComponent(final Component oldComponent, final Component newComponent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addComponent(final Component c) {
		throw new UnsupportedOperationException();
	}

	public void addComponent(final Component c, final GridsterWidgetPosition position) {
		if (connectorToId.containsKey(c)) {
			throw new IllegalArgumentException("Component is already attached to this layout");
		}

		final String id = UUID.randomUUID().toString();

		final GridsterWidget widget = new GridsterWidget(id, c, position);
		children.put(id, widget);
		connectorToId.put(c, id);

		getState().children.put(c, widget);
		super.addComponent(c);

		fireEvent(new StateChangeEvent(this, getLayoutState()));
	}

	@Override
	public void removeComponent(final Component c) {
		removeComponent(c, true);
	}

	private void removeComponent(final Component c, final boolean fireEvent) {
		if (!connectorToId.containsKey(c)) {
			return;
		}

		final String id = connectorToId.get(c);

		children.remove(id);
		connectorToId.remove(c);

		getState().children.remove(c);
		super.removeComponent(c);

		fireEvent(new StateChangeEvent(this, getLayoutState()));
	}

	@Override
	public void removeAllComponents() {
		final LinkedList<Component> l = new LinkedList<Component>();

		// Adds all components
		for (final Component component : this) {
			l.add(component);
		}

		// Removes all component
		for (final Component component : l) {
			removeComponent(component, false);
		}

		fireEvent(new StateChangeEvent(this, getLayoutState()));
	}

	private void dragged0(final String id, final Map<String, GridsterWidgetPosition> newPositions) {
		synchonizeRpcPositionsWithState(newPositions);
	}

	/**
	 * Updates the position of the component
	 *
	 * @param c
	 * @param position
	 */
	public void setComponentPosition(final Component c, final GridsterWidgetPosition position) {

	}

	private void resized0(final String id, final Map<String, GridsterWidgetPosition> newPositions) {
		synchonizeRpcPositionsWithState(newPositions);

		final GridsterWidget widget = children.get(id);

		final Connector c = widget.getConnector();

		if (c instanceof ResizeAwareComponent) {
			((ResizeAwareComponent) c).onResize(widget.getPosition().clone());
		}

		fireEvent(new ComponentResizeEvent(this, (Component) c, widget.getPosition().clone()));
	}

	private void synchonizeRpcPositionsWithState(final Map<String, GridsterWidgetPosition> newPositions) {
		final Map<Component, GridsterWidgetPosition> eventPositions = new HashMap<Component, GridsterWidgetPosition>();

		for (final Map.Entry<String, GridsterWidgetPosition> entry : newPositions.entrySet()) {
			final String id = entry.getKey();
			final GridsterWidgetPosition position = entry.getValue();

			final GridsterWidget widget = children.get(id);

			if (widget == null) {
				System.out.println("ERROR: no widget with ID " + id + " found in internal state");
				continue;
			}

			// this also synchronizes the getState() state because it is the
			// reference-equal object. I know this is dirty, but we also don't
			// want to trigger a markAsDirty
			widget.setPosition(position.clone());

			eventPositions.put((Component) widget.getConnector(), position.clone());
		}

		fireEvent(new StateChangeEvent(this, eventPositions));
	}

	public Map<Component, GridsterWidgetPosition> getLayoutState() {
		final Map<Component, GridsterWidgetPosition> positions = new HashMap<Component, GridsterWidgetPosition>();

		for (final GridsterWidget widget : children.values()) {
			positions.put((Component) widget.getConnector(), widget.getPosition().clone());
		}

		return positions;
	}

	@Override
	public int getComponentCount() {
		return children.size();
	}

	@Override
	public Iterator<Component> iterator() {
		return connectorToId.keySet().iterator();
	}

	public void addStateChangeListener(final StateChangeListener listener) {
		addListener(StateChangeEvent.class, listener, StateChangeListener.stateChangeMethod);
	}

	public void removeStateChangeListener(final StateChangeListener listener) {
		removeListener(StateChangeEvent.class, listener, StateChangeListener.stateChangeMethod);
	}

	public void addComponentResizeListener(final ComponentResizeListener listener) {
		addListener(ComponentResizeEvent.class, listener, ComponentResizeListener.componentResizeMethod);
	}

	public void removeComponentResizeListener(final ComponentResizeListener listener) {
		removeListener(ComponentResizeEvent.class, listener, ComponentResizeListener.componentResizeMethod);
	}

}
