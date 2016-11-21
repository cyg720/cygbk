package com.cyg.framework.utils.excel.excel2007;

import java.util.ArrayList;
import java.util.List;


/**
 * Excel行对象封装
 * Created by WL on 2015/12/25.
 */
public class XRow {
	private int rowIndex;
	private List<XCell> cells=new ArrayList<XCell>();
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getCellsSize() {
		return cells.size();
	}
	public XRow addCell(XCell cell){
		this.cells.add(cell);
		return this;
	}
	public XCell getCell(int cellIndex){
		return cells.get(cellIndex);
	}
}
