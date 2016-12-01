package com.cyg.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
/**
 * 
 * @ClassName TableName
 * @Description 创建表的注解
 * @author 陈一国
 * @Date 2016年11月22日 下午10:16:24
 * @version 1.0.0
 */
@Component
@Documented//注释
@Target(value=ElementType.TYPE)//使用位置
@Retention(RetentionPolicy.RUNTIME)//保存范围 
public @interface TableName {
	public String tableName() default "";
}
