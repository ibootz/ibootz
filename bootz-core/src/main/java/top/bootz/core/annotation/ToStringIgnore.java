package top.bootz.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 调用ToStringHelper格式化对象时，忽略标注了该注解的字段(暂未实现)
 * 
 * @author John
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToStringIgnore {

	String value() default "";

}
