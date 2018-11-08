package top.bootz.security.web.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 记录安全组件中请求日志信息的切面
 *
 * @author Zhangq
 */

@Aspect
@Component
public class AccessHandlerAdvice {

	private static final String CONTROLLER_EXECUTION = "execution(* top.bootz.security..*Controller.**(..)) "
			+ " || execution(* top.bootz.security..controller..*.**(..))";

	@Pointcut(CONTROLLER_EXECUTION)
	private void pointcutInControllerLayer() {
		// do nothing
	}

	@Before(value = "pointcutInControllerLayer()")
	public void authorize(JoinPoint joinPoint) {
		getControllerMethod(joinPoint);
		// do something
	}

	@After(value = "pointcutInControllerLayer()")
	public void logTime(JoinPoint joinPoint) {
		getControllerMethod(joinPoint);
		// do something
	}

	@AfterReturning(value = "pointcutInControllerLayer()")
	public void logSuccessEvent(JoinPoint joinPoint) {
		getControllerMethod(joinPoint);
		// do something
	}

	@AfterThrowing(value = "pointcutInControllerLayer()", throwing = "ex")
	public void logFailEvent(JoinPoint joinPoint, Exception ex) {
		getControllerMethod(joinPoint);
		// do something
	}

	private Method getControllerMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod();
	}

}
