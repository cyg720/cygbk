package com.cyg.framework.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，标记该注解的类可使用父类导入导出功能
 * @author PT
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {
	String name() default "导出";
	
	/**
	 * 导入时需要读取的sheet排序号
	 * @return
	 */
	int sheetNum() default 0;
}
