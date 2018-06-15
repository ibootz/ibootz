package top.bootz.user.web.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.SecurityConstants;
import top.bootz.commons.exception.ApiException;
import top.bootz.commons.helper.DateHelper;
import top.bootz.commons.helper.HttpHelper;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.commons.helper.ReflectionHelper;
import top.bootz.commons.helper.ToStringHelper;
import top.bootz.core.aspect.AccessAdviceInfo;
import top.bootz.core.aspect.AdviceException;

/**
 * 记录请求日志信息的切面
 * 
 * @author dong
 *
 */
@Order(1)
@Aspect
@Slf4j
public class AccessHandleAdvice {

	private static final String CONTROLLER_EXECUTION = "execution(public * com.orion.manage.user.controller..*.*(..))";

	@Autowired
	private MessageSource messageSource;

	// @Autowired
	// private TokenService tokenService;

	@Pointcut(CONTROLLER_EXECUTION)
	private void pointcutInControllerLayer() {
		// do nothing
	}

	/**
	 * 记录方法执行前的请求路径,请求人信息,并使用日志打印出来
	 * 
	 * @throws Throwable
	 */
	@Around(value = "pointcutInControllerLayer()")
	public Object doAroundInControllerLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = getHttpServletRequest();
		MethodSignature methodSignature = getMethodSignature(joinPoint);
		Method method = methodSignature.getMethod();
		boolean isAccessible = method.isAccessible();
		method.setAccessible(true);
		String[] paramNames = methodSignature.getParameterNames();
		String returnType = methodSignature.getReturnType().getSimpleName();
		AccessAdviceInfo accessAdviceInfo = new AccessAdviceInfo();
		// visitor
		String token = request.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);

		// TODO 从token中获取用户信息，存入日志系统
		// String username = this.tokenService.getUsernameFromToken(token);
		// accessAdviceInfo.setVisitor(username);

		// token
		accessAdviceInfo.setToken(token);
		// visitorIp
		String remoteHost = HttpHelper.getRemoteHost(request);
		accessAdviceInfo.setVisitorIp(remoteHost);
		// requestURL
		accessAdviceInfo.setRequestURL(request.getRequestURL().toString());
		// className
		accessAdviceInfo.setClassName(joinPoint.getTarget().getClass().getName());
		// methodName
		accessAdviceInfo.setMethodName(joinPoint.getSignature().getName());
		// inputParamMap
		Object[] args = joinPoint.getArgs();
		if (!ArrayUtils.isEmpty(args)) {
			putInputParams(paramNames, accessAdviceInfo, args);
		}
		// hasResponse
		accessAdviceInfo.setReturned(!"void".equalsIgnoreCase(returnType));
		long start = System.currentTimeMillis();
		long tookMillSeconds = 0;
		Object object = null;
		boolean successed = true;
		Date exceptionTime = null;
		String errMsg = "";
		AdviceException adviceException = null;
		try {
			object = joinPoint.proceed();
			tookMillSeconds = System.currentTimeMillis() - start;
		} catch (Throwable e) {
			successed = false;
			exceptionTime = DateHelper.now();
			tookMillSeconds = System.currentTimeMillis() - start;
			errMsg = getErrMsg(e);
			adviceException = new AdviceException(errMsg, e);
			log.error(e.getMessage());
			throw e;
		} finally {
			// response
			if (accessAdviceInfo.isReturned() && object != null) {
				accessAdviceInfo.setResponse(ToStringHelper.toJSON(object));
			}
			// tookMillSeconds
			accessAdviceInfo.setTookMillSeconds(tookMillSeconds);
			// successed
			accessAdviceInfo.setSuccessed(successed);
			if (!accessAdviceInfo.isSuccessed() && exceptionTime != null) {
				accessAdviceInfo.setExceptionTime(DateHelper.date2str(exceptionTime));
			}
			// errMsg
			accessAdviceInfo.setErrMsg(errMsg);
			// adviceException
			accessAdviceInfo.setAdviceException(adviceException);
			method.setAccessible(isAccessible);
			log.info(JsonHelper.toJSON(accessAdviceInfo));
		}
		return object;
	}

	private void putInputParams(String[] paramNames, AccessAdviceInfo accessAdviceInfo, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			Class<?> clz = args[i].getClass();
			boolean isPolarisClass = clz.getName().startsWith("com.orion");
			boolean isSimpleClass = ReflectionHelper.isBaseClassOrString(clz) || ReflectionHelper.isCollection(clz)
					|| ReflectionHelper.isMap(clz);
			// 异常处理切面中方法的参数太过于复杂，这里没有必要作为日志打印详细信息，故排除掉
			boolean isNotExceptionClass = !clz.getName().endsWith("Exception");
			if ((isPolarisClass || isSimpleClass) && isNotExceptionClass) {
				accessAdviceInfo.putInputParam(paramNames[i], ToStringHelper.toJSON(args[i]));
			} else {
				accessAdviceInfo.putInputParam(paramNames[i], args[i].toString());
			}
		}
	}

	private String getErrMsg(Throwable e) {
		String errMsg;
		if (e instanceof ApiException) {
			ApiException apiException = (ApiException) e;
			errMsg = messageSource.getMessage(apiException.getErrorKey(), apiException.getArgs(), e.getMessage(), null);
		} else if (e instanceof NullPointerException) {
			errMsg = "NullPointerException";
		} else {
			errMsg = e.getMessage();
		}
		return errMsg;
	}

	private HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest();
	}

	private MethodSignature getMethodSignature(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		return (MethodSignature) signature;
	}

}
