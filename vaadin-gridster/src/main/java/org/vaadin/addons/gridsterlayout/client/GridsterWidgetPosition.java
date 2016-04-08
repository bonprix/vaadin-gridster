package org.vaadin.addons.gridsterlayout.client;

public class GridsterWidgetPosition implements Cloneable {

	private int col = 1;
	private int row = 1;
	private int sizeX = 1;
	private int sizeY = 1;

	public GridsterWidgetPosition() {

	}

	public GridsterWidgetPosition(final GridsterWidgetPosition copy) {
		this(copy.col, copy.row, copy.sizeX, copy.sizeY);
	}

	public GridsterWidgetPosition(final int col, final int row, final int sizeX, final int sizeY) {
		super();
		this.col = col;
		this.row = row;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public GridsterWidgetPosition clone() {
		return new GridsterWidgetPosition(this);
	}

	public void setCol(final int col) {
		this.col = col;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	public void setSizeX(final int sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(final int sizeY) {
		this.sizeY = sizeY;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	@Override
	public String toString() {
		return "[col=" + col + ", row=" + row + ", sizeX=" + sizeX + ", sizeY=" + sizeY + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + sizeX;
		result = prime * result + sizeY;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GridsterWidgetPosition other = (GridsterWidgetPosition) obj;
		if (col != other.col) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		if (sizeX != other.sizeX) {
			return false;
		}
		if (sizeY != other.sizeY) {
			return false;
		}
		return true;
	}

}
