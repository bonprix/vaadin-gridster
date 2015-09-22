package org.vaadin.addons.gridsterlayout;

import java.lang.reflect.Method;
import java.util.Map;

import org.vaadin.addons.gridsterlayout.client.GridsterWidgetPosition;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;
import com.vaadin.util.ReflectTools;

public interface StateChangeListener {

	public static class StateChangeEvent extends Event {

		private Map<Component, GridsterWidgetPosition> position;

		public StateChangeEvent(final Component target, final Map<Component, GridsterWidgetPosition> position) {
			super(target);
			this.position = position;
		}

		public Map<Component, GridsterWidgetPosition> getPosition() {
			return position;
		}

		public void setPosition(final Map<Component, GridsterWidgetPosition> position) {
			this.position = position;
		}
	}

	public static final Method stateChangeMethod = ReflectTools.findMethod(StateChangeListener.class, "onStateChange", StateChangeEvent.class);

	void onStateChange(final StateChangeEvent event);

}
