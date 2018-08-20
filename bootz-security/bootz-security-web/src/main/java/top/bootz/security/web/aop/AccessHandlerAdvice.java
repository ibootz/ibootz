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

import lombok.extern.slf4j.Slf4j;

/**
 * 记录请求日志信息的切面
 *
 * @author Zhangq
 */
@Slf4j
@Aspect
@Component
public class AccessHandlerAdvice {

	private static final String CONTROLLER_EXECUTION = "execution(* top.bootz.security..controller..*.**(..))";

	// @Autowired
	// private TokenService tokenService;

	@Pointcut(CONTROLLER_EXECUTION)
	private void pointcutInControllerLayer() {
		// do nothing
	}

	@Before(value = "pointcutInControllerLayer()")
	public void authorize(JoinPoint joinPoint) {
		log.debug("@Before - 2");
	}

	@After(value = "pointcutInControllerLayer()")
	public void logTime(JoinPoint joinPoint) {
		log.debug("@After - 2");
	}
	
	@AfterReturning(value = "pointcutInControllerLayer()")
	public void logSuccessEvent(JoinPoint joinPoint) {
		log.debug("@AfterReturning - 2");
	}

	@AfterThrowing(value = "pointcutInControllerLayer()", throwing = "ex")
	public void logFailEvent(JoinPoint joinPoint, Exception ex) {
		log.debug("@AfterThrowing - 2");
	}

	private Method getControllerMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod();
	}

}
