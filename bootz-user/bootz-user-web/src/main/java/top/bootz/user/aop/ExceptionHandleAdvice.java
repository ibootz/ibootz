package top.bootz.user.aop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import top.bootz.core.base.aop.BaseExceptionHandleAdvice;

/**
 * 专门用来处理带有RestController和Controller注解的controller层面的异常.
 * 异常匹配顺序是从上到下，匹配到合适的异常处理程序之后就不再向下匹配
 * 
 * @author John
 *
 */

@RestControllerAdvice(annotations = { RestController.class, Controller.class })
public class ExceptionHandleAdvice extends BaseExceptionHandleAdvice {

}
