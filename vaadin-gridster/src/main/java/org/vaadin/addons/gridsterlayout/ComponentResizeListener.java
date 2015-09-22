package org.vaadin.addons.gridsterlayout;

import java.lang.reflect.Method;

import org.vaadin.addons.gridsterlayout.client.GridsterWidgetPosition;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;
import com.vaadin.util.ReflectTools;

public interface ComponentResizeListener {

	public static class ComponentResizeEvent extends Event {

		private final Component target;
		private final GridsterWidgetPosition position;

		public ComponentResizeEvent(final Component source, final Component target, final GridsterWidgetPosition position) {
			super(source);
			this.target = target;
			this.position = position;
		}

		public Component getTarget() {
			return target;
		}

		public GridsterWidgetPosition getPosition() {
			return position;
		}
	}

	public static final Method componentResizeMethod = ReflectTools.findMethod(ComponentResizeListener.class, "onComponentResize", ComponentResizeEvent.class);

	void onComponentResize(final ComponentResizeEvent event);

}
