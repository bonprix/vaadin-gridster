package org.vaadin.addons.gridsterlayout.client;

import java.util.Map;

import com.vaadin.shared.communication.ServerRpc;

// ServerRpc is used to pass events from client to server
public interface GridsterLayoutServerRpc extends ServerRpc {

	public void dragged(final String id, final Map<String, GridsterWidgetPosition> state);

	public void resized(final String id, final Map<String, GridsterWidgetPosition> state);

}
