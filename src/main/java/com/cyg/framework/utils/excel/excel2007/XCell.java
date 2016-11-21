package com.cyg.framework.utils.excel.excel2007;

/**
 * Excel单元格对象封装
 * Created by WL on 2015/12/25.
 */
public class XCell {
	private int rowIndex;
	private int columnIndex;
	private String value;
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
