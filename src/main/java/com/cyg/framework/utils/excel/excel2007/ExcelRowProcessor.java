package com.cyg.framework.utils.excel.excel2007;

import java.io.IOException;
import java.io.InputStream;

/**
 * 接口，Excel行级处理器
 * Created by WL on 2015/12/25.
 */
public interface ExcelRowProcessor{
	public void processByRow() throws Exception;
	public void processByRow(int sheetIndex) throws Exception;
	public void processByRow(InputStream file) throws Exception;
	public void processRow(XRow row, Boolean isNull);
	public void stop() throws IOException;
}
