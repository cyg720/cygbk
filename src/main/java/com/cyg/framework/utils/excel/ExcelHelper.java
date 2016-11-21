package com.cyg.framework.utils.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;

import com.cyg.framework.utils.BeanUtil;
import com.cyg.framework.utils.DateUtil;
import com.cyg.framework.utils.excel.excel2007.XRow;

/**
 * 
 * @author PT
 *
 */
public class ExcelHelper {
	
	
	/**
	 * 根据注解组装excel数据并按列排序
	 * @param clazz
	 * @param list
	 * @return
	 */
	public static List<List<String>> assemblyData(Class<?> clazz, List<?> list){
		List<List<String>> resultList = new ArrayList<List<String>>();
		Field[] fields = getFieldListWithIndex(clazz);
		List<String> titleList = new ArrayList<>();
		for(Field field : fields){
			titleList.add(field.getAnnotation(ExcelColumn.class).name());
		}
		resultList.add(titleList);
		for(Object m : list){
			List<String> valueList = new ArrayList<>();
			for(Field field : fields){
				Object value = BeanUtil.getPropertyValue(m, field.getName());
				if(value == null){
					valueList.add("");
					continue;
				}
				if(value instanceof Date){
					valueList.add(DateUtil.datetimeToString((Date)value));
					continue;
				}
				valueList.add(value.toString());
			}
			resultList.add(valueList);
		}
		return resultList;
	}
	
	/**
	 * 获取被注解的属性并排序
	 * @param clazz
	 * @return
	 */
	public static Field[] getFieldListWithIndex(Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		List<Field> fieldList = new ArrayList<>();
		for(Field field : fields){
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			//未注解或复杂属性过滤
			if(annotation==null || !BeanUtils.isSimpleProperty(field.getType())){
				continue;
			}
			fieldList.add(field);
		}
		fields = (Field[]) fieldList.toArray(new Field[fieldList.size()]);
		//定 义比较器
		class MyComparator implements Comparator<Field>{
			@Override
			public int compare(Field f1, Field f2) {
				ExcelColumn annotation1 = f1.getAnnotation(ExcelColumn.class);
				ExcelColumn annotation2 = f2.getAnnotation(ExcelColumn.class);
				int index1 = annotation1.index();
				int index2 = annotation2.index();
				if(index1<index2){
					return -1;
				}else if(index1>index2){
					return 1;
				}
				return 0;
			}			
		}
		//排序
		Arrays.sort(fields, new MyComparator());
		return fields;
	}
	
	/**
	 * 生成excel
	 * @param list
	 * @param title
	 * @return
	 */
	public static HSSFWorkbook createExcel(List<List<String>> list, String title){
		// 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        
        for(int i=0;i<list.size();i++){
        	HSSFRow row = sheet.createRow(i);
        	List<String> strList = list.get(i);
        	for(int j=0;j<strList.size();j++){
        		HSSFCell cell = row.createCell(j);
        		String str = strList.get(j);
        		if(sheet.getColumnWidth(j)<str.getBytes().length*256){
        			sheet.setColumnWidth(j, str.getBytes().length*256);
        		}
                HSSFRichTextString text = new HSSFRichTextString(strList.get(j));
                cell.setCellValue(text);
        	}
        }
        return workbook;
	}
	
	/**
	 * excel数据拼装成实体对象list
	 * 2015/5/6
	 * 1：添加对类的注解检测，
	 * 2：添加泛型支持
	 * 3：读取@Excel注解中sheetNum指定的表序号。
	 * @param clazz
	 * @param workbook
	 * @return
	 * @throws Exception
	 */
	
	public static <T>  List<T> convert(Class<T> clazz, HSSFWorkbook workbook) throws Exception{
		List<T> objList = new ArrayList<>();
		Excel annotation = clazz.getAnnotation(Excel.class);
		if(annotation==null) return objList;
		int sheetNo=annotation.sheetNum();
		Sheet sheet = workbook.getSheetAt(sheetNo);
		Field[] fields = getFieldListWithIndex(clazz);
		
		//跳过表头
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
            Row row = sheet.getRow(i); 
            if(row == null){ 
                continue; 
            }
            T obj = clazz.newInstance();
            for(int j = 0; j < row.getLastCellNum(); j++){ 
                Cell cell = row.getCell(j);
                String value = "";
                if(cell == null)continue;
                switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                	value = cell.getStringCellValue().trim();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                	value = String.valueOf(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                	value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                	value = "";
                    break;
                default:
                	value = "";
                    break;
                }
                try {
                	Field field = fields[j];
                	fields[j].setAccessible(true);
					if(Boolean.class.equals(field.getType())){
						if("false".equals(value.toLowerCase()) || "0".equals(value)){
							field.set(obj, false);
						}else{
							field.set(obj, true);
						}
					}else if(Integer.class.equals(field.getType())){
						field.set(obj, Integer.parseInt(value));
					}else if(Long.class.equals(field.getType())){
						field.set(obj, Long.parseLong(value));
					}else if(Float.class.equals(field.getType())){
						field.set(obj, Float.parseFloat(value));
					}else if(Double.class.equals(field.getType())){
						field.set(obj, Double.parseDouble(value));
					}else if(Short.class.equals(field.getType())){
						field.set(obj, Short.parseShort(value));
					}else if(Date.class.equals(field.getType())){
						field.set(obj, DateUtil.stringToDatetime(value));
					}else if(String.class.equals(field.getType())){
						field.set(obj,value);
					}
				} catch (Exception e) {
					continue;
				} 
            }
            objList.add(obj);
        }
        return objList;
	}

	public static <T>  List<T> convert(Class<T> clazz, List<XRow> list) throws Exception {
		List<T> objList = new ArrayList<>();
		Excel annotation = clazz.getAnnotation(Excel.class);
		if (annotation == null) return objList;
		Field[] fields = getFieldListWithIndex(clazz);
		for (XRow row : list) {
			T obj = clazz.newInstance();
			if (row == null) {
				continue;
			}
			for (int i = 0; i < row.getCellsSize(); i++) {
				System.out.print(row.getCell(i).getValue() + ",");
				String value = row.getCell(i).getValue().trim();
				try {
					Field field = fields[i];
					fields[i].setAccessible(true);
					if (Boolean.class.equals(field.getType())) {
						if ("false".equals(value.toLowerCase()) || "0".equals(value)) {
							field.set(obj, false);
						} else {
							field.set(obj, true);
						}
					} else if (Integer.class.equals(field.getType())) {
						field.set(obj, Integer.parseInt(value));
					} else if (Long.class.equals(field.getType())) {
						field.set(obj, Long.parseLong(value));
					} else if (Float.class.equals(field.getType())) {
						field.set(obj, Float.parseFloat(value));
					} else if (Double.class.equals(field.getType())) {
						String s = value;
						if(s.indexOf(".") > 0){
							//正则表达
							s = s.replaceAll("0+?$", "");//去掉后面无用的零
							s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
						}
						field.set(obj, Double.parseDouble(s));
					} else if (Short.class.equals(field.getType())) {
						field.set(obj, Short.parseShort(value));
					} else if (Date.class.equals(field.getType())) {
						field.set(obj, DateUtil.stringToDatetime(value));
					} else if (String.class.equals(field.getType())) {
						field.set(obj, value);
					}
				} catch (Exception e) {
					continue;
				}
			}
			objList.add(obj);
		}
		return objList;
	}
}
