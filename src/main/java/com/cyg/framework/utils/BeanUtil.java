package com.cyg.framework.utils;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @Description: java bean 工具类
 * @author 王道兵
 */
@SuppressWarnings({ "rawtypes" })
public class BeanUtil {

	/**
	 * 获取一个对象的属性值。支持对象级联。 对象...对象.属性 格式 对象.属性 格式
	 * 
	 * @param obj 目标对象
	 * @param property 属性字符串
	 * @return 未找到返回null
	 */
	public static Object getPropertyValue(Object obj, String property) {
		Object value = null;
		try {
			if (obj != null && StringUtil.isNotBlank(property)) {
				if (property.contains(".")) {
					String[] propertys = property.split("\\.");
					Object tem = obj;
					for (String p : propertys) {
						if (tem == null)
							break;
						tem = PropertyUtils.getProperty(tem, p);
					}
					value = tem;
				} else {
					value = PropertyUtils.getProperty(obj, property);
				}
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 获取一个对象的属性类型。支持对象级联模式。
	 * 
	 * @param obj
	 * @param property
	 * @return 未找到返回null
	 */
	public static Class getBeanPropertyType(Class cls, String property) {
		Class target = null;
		try {
			if (cls != null && StringUtil.isNotBlank(property)) {
				if (property.contains(".")) {
					String[] propertys = property.split("\\.");
					Class tem = cls;
					for (String p : propertys) {
						if (tem == null)
							break;

						// Field field=tem.getDeclaredField(p);
						Field field = getField(tem, p);
						if (field == null)
							break;
						tem = (Class) field.getGenericType();
					}
					target = tem;
				} else {
					Field field = cls.getDeclaredField(property);
					if (field != null) {
						target = (Class) field.getGenericType();
					}
				}
			}
		} catch (Exception e) {
		}
		return target;
	}

	/**
	 * 递归查找一个类或父类的字段
	 * 
	 * @param cls
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	
	private static Field getField(Class cls, String fieldName) {
		Field field = null;
		try {
			field = cls.getDeclaredField(fieldName);
		} catch (Exception e) {}
		if (field == null) {
			Class superClass = cls.getSuperclass();
			if (superClass != cls) {
				field = getField(superClass, fieldName);
			}
		}
		return field;
	}
}
