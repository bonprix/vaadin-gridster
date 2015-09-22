package org.vaadin.addons.gridsterlayout.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.StyleConstants;

// Extend any GWT Widget
public class VGridsterLayout extends ComplexPanel {

	public static final String CLASSNAME = "v-gridsterlayout";

	private final Element contentDiv;
	private final String gridsterId;
	private JavaScriptObject gridster;
	private GridsterConfig config;

	private boolean initialized = false;

	public int i = 0;

	private GridsterListener listener;

	public VGridsterLayout() {
		gridsterId = DOM.createUniqueId();

		final Element gridsterWrapper = DOM.createElement("div");
		gridsterWrapper.addClassName("gridster");
		gridsterWrapper.setId(gridsterId);

		final Element e = DOM.createDiv();
		e.appendChild(gridsterWrapper);
		setElement(e);

		contentDiv = DOM.createElement("ul");
		gridsterWrapper.appendChild(contentDiv);

		// Clear any unwanted styling
		final Style style = getElement().getStyle();
		style.setBorderStyle(BorderStyle.NONE);
		style.setMargin(0, Unit.PX);
		style.setPadding(0, Unit.PX);

		if (BrowserInfo.get().isIE()) {
			style.setPosition(Position.RELATIVE);
		}

		setStyleName(CLASSNAME);

		addAttachHandler(new Handler() {

			@Override
			public void onAttachOrDetach(final AttachEvent event) {
				checkInit();
			}
		});
	}

	@Override
	public void setStyleName(final String style) {
		super.setStyleName(style);
		addStyleName(StyleConstants.UI_LAYOUT);
	}

	public void updateCaption(final ComponentConnector paintable) {

	}

	public void addWidget(final String id, final Widget widget, final GridsterWidgetPosition position) {
		checkInit();

		addNativeWidget(gridsterId, id, position.getCol(), position.getRow(), position.getSizeX(), position.getSizeY());

		add(widget, DOM.getElementById(id));
	}

	@Override
	public boolean remove(final Widget oldChildWidget) {

		// TODO Auto-generated method stub
		return true;
	}

	private void checkInit() {
		if (initialized || config == null || !isAttached()) {
			return;
		}

		initialized = true;
		initNative(gridsterId, config.getMinCols(), config.getMaxCols(), config.getWidgetBaseDimensionX(), config.getWidgetBaseDimensionY(),
				config.getWidgetMargins());
	}

	private void onDrag(final String id) {
		listener.dragged(id);
	}

	private void onResize(final String id) {
		listener.resized(id);
	}

	public GridsterWidgetPosition getPositions(final String widgetId) {
		final Element e = DOM.getElementById(widgetId);

		if (e == null) {
			return null;
		}

		final int col = Integer.parseInt(e.getAttribute("data-col"));
		final int row = Integer.parseInt(e.getAttribute("data-row"));
		final int sizeX = Integer.parseInt(e.getAttribute("data-sizex"));
		final int sizeY = Integer.parseInt(e.getAttribute("data-sizey"));

		return new GridsterWidgetPosition(col, row, sizeX, sizeY);
	}

	private native void initNative(final String gridsterId, final int minCol, final int maxCol, final int widgetWidth, final int widthHeight, final int margins)
	/*-{
	 var self = this;

	 this.@org.vaadin.addons.gridsterlayout.client.VGridsterLayout::gridster = $wnd.$("#" + gridsterId + " > ul").gridster({
	 	widget_base_dimensions: [widgetWidth, widthHeight],
	    widget_margins: [margins, margins],
	    autogrow_cols: true,
	    min_cols: minCol,
	    max_cols: maxCol,
	    resize: {
	    	enabled: true,
	    	stop: function(e, ui, widget) {
	    				self.@org.vaadin.addons.gridsterlayout.client.VGridsterLayout::onResize(Ljava/lang/String;)(widget[0].id);
	    			}
	    },
	    draggable: {
	    	stop: function(e, ui, widget) {
	    				self.@org.vaadin.addons.gridsterlayout.client.VGridsterLayout::onDrag(Ljava/lang/String;)(e.target.id);
	    			}
	    }
	 }).data('gridster');
	 }-*/;

	private native void addNativeWidget(final String gridsterId, final String id, final int col, final int row, final int sizeX, final int sizeY) /*-{
	                                                                                                                                              this.@org.vaadin.addons.gridsterlayout.client.VGridsterLayout::gridster.add_widget('<li id="'+id+'"></li>', sizeX, sizeY, col, row);
	                                                                                                                                              }-*/;

	public GridsterConfig getConfig() {
		return config;
	}

	public void setConfig(final GridsterConfig config) {
		this.config = config;

		checkInit();
	}

	public void setServerRpc(final GridsterListener listener) {
		this.listener = listener;
	}

}