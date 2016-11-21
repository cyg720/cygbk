package com.cyg.framework.utils.excel.excel2007;

import java.io.IOException;
import java.io.InputStream;

/**
 * Excel处理器，兼容Excel-2003与Excel-2007
 * @author zhangchaofeng
 * @version 1.0
 * @date Sep 29, 2011
 */
public abstract class ExcelProcessor implements ExcelRowProcessor{
	private ExcelRowProcessor processor;

	public ExcelProcessor(){
		processor = new MyExcel2007RowProcessor();
	}

	public void processByRow() throws Exception {
		processor.processByRow();
	}

	public void processByRow(int sheetIndex) throws Exception {
		processor.processByRow(sheetIndex);
	}

	public void processByRow(InputStream file) throws Exception {
		processor.processByRow(file);
	}
	
	public void stop() throws IOException {
		processor.stop();
	}

	public abstract void processRow(XRow row,Boolean isNull);
	
	private class MyExcel2007RowProcessor extends Excel2007RowProcessor{
		public MyExcel2007RowProcessor(){

		}
		@Override
		public void processRow(XRow row ,Boolean isNull) {
			ExcelProcessor.this.processRow(row,isNull);
		}
	}
}
