package org.vaadin.addons.gridsterlayout.client;

public class GridsterConfig {

	private int minCols = 0;
	private int maxCols = 20;

	private int widgetMargins = 5;

	private int widgetBaseDimensionX = 200;
	private int widgetBaseDimensionY = 200;

	private boolean resizeable = false;

	public int getMinCols() {
		return minCols;
	}

	public void setMinCols(final int minCols) {
		this.minCols = minCols;
	}

	public int getMaxCols() {
		return maxCols;
	}

	public void setMaxCols(final int maxCols) {
		this.maxCols = maxCols;
	}

	public int getWidgetMargins() {
		return widgetMargins;
	}

	public void setWidgetMargins(final int widgetMargins) {
		this.widgetMargins = widgetMargins;
	}

	public int getWidgetBaseDimensionX() {
		return widgetBaseDimensionX;
	}

	public void setWidgetBaseDimensionX(final int widgetBaseDimensionX) {
		this.widgetBaseDimensionX = widgetBaseDimensionX;
	}

	public int getWidgetBaseDimensionY() {
		return widgetBaseDimensionY;
	}

	public void setWidgetBaseDimensionY(final int widgetBaseDimensionY) {
		this.widgetBaseDimensionY = widgetBaseDimensionY;
	}

	public boolean isResizeable() {
		return resizeable;
	}

	public void setResizeable(final boolean resizeable) {
		this.resizeable = resizeable;
	}

}
