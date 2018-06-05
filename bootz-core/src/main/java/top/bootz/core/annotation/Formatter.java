package com.orion.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用指定的格式，来格式化其修饰的参数或者字段
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Formatter {

	/**
	 * 为该参数指定一个名称，默认不指定的话，使用参数名
	 * 
	 * @return
	 */
	String value();

	/**
	 * 为参数指定一个格式化工具类，没有指定的话，默认采用ToStringBuilder.toJson()方法来格式化
	 * 
	 * @return
	 */
	Class<?> formatter();

}
