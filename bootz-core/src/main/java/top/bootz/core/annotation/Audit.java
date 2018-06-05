package com.orion.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.orion.common.constant.audit.AuditLogObject;
import com.orion.common.constant.audit.AuditLogOperation;

/**
 * 审计注解，方法级注解，凡是标注上该注解的方法，都需要在切面方法中进行审计统计，打印出日志
 * 
 * @author dong
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Audit {

	/**
	 * 操作类型
	 * 
	 * @return
	 */
	AuditLogOperation op();

	/**
	 * 操作对象
	 * 
	 * @return
	 */
	AuditLogObject obj();

	/**
	 * 自定义记录信息
	 * 
	 * @return
	 */
	String comment();

}
