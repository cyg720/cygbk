package com.cyg.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @ClassName TableColumn
 * @Description 表列的属性
 * @author 陈一国
 * @Date 2016年11月22日 下午10:18:38
 * @version 1.0.0
 */
//@Inherited 加上这个注解说明该annotation 可以被子类所继承
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {

    public String name() default "";
    
    public String type() default "";
    
    public int length();
    
}
