package top.bootz.core.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.ExceptionConstants;
import top.bootz.commons.constant.SecurityConstants;
import top.bootz.commons.exception.AdviceException;
import top.bootz.commons.exception.ApiException;
import top.bootz.commons.helper.DateHelper;
import top.bootz.commons.helper.HttpHelper;
import top.bootz.commons.helper.ReflectionHelper;
import top.bootz.commons.helper.ToStringHelper;
import top.bootz.core.log.AccessLog;
import top.bootz.core.log.AccessLogService;

/**
 * 记录请求日志信息的切面
 *
 * @author Zhangq
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@Aspect
@Component
public class AccessHandlerAdvice {

	private static final String CONTROLLER_EXECUTION = "execution(* top.bootz..controller..*.**(..))";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccessLogService accessLogService;

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
		HttpServletRequest request = null;
		Method method = null;
		boolean isAccessible = false;
		long tookMillSeconds = 0;
		Object object = null;
		boolean successed = true;
		Date exceptionTime = null;
		String errMsg = "";
		AdviceException adviceException = null;
		AccessLog accessLog = new AccessLog();
		long start = 0L;

		try {
			Optional<HttpServletRequest> requestOpt = getHttpServletRequest();
			if (requestOpt.isPresent()) {
				request = requestOpt.get();
			} else {
				throw new ApiException(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.ErrorMessageKey.BAD_REQUEST);
			}

			MethodSignature methodSignature = getMethodSignature(joinPoint);
			method = methodSignature.getMethod();
			isAccessible = method.isAccessible();
			method.setAccessible(true);
			String[] paramNames = methodSignature.getParameterNames();
			String returnType = methodSignature.getReturnType().getSimpleName();

			// visitor
			String token = request.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);

			// TODO 从token中获取用户信息，存入日志系统
			// String username = this.tokenService.getUsernameFromToken(token);
			// accessLog.setVisitor(username);

			// token
			accessLog.setToken(token);
			// requestIp
			String remoteHost = HttpHelper.getRemoteHost(request);
			accessLog.setRequestIp(remoteHost);
			// requestUrl
			accessLog.setRequestUrl(request.getRequestURL().toString());
			// className
			accessLog.setClassName(joinPoint.getTarget().getClass().getName());
			// methodName
			accessLog.setMethodName(joinPoint.getSignature().getName());
			// inputParamMap
			Object[] args = joinPoint.getArgs();
			if (!ArrayUtils.isEmpty(args)) {
				putInputParams(paramNames, accessLog, args);
			}
			// hasResponse
			accessLog.setReturned(!"void".equalsIgnoreCase(returnType));
			start = System.currentTimeMillis();

			object = joinPoint.proceed();
			tookMillSeconds = System.currentTimeMillis() - start;
		} catch (Throwable e) {
			successed = false;
			exceptionTime = DateHelper.now();
			tookMillSeconds = System.currentTimeMillis() - start;
			errMsg = getErrMsg(request, e);
			adviceException = new AdviceException(errMsg, e);
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			// response
			if (accessLog.isReturned() && object != null) {
				accessLog.setResponse(ToStringHelper.toJSON(object, false));
			}
			// tookMillSeconds
			accessLog.setTookMillSeconds(tookMillSeconds);
			// successed
			accessLog.setSuccessed(successed);
			if (!accessLog.isSuccessed() && exceptionTime != null) {
				accessLog.setExceptionTime(DateHelper.date2str(exceptionTime));
			}
			// errMsg
			accessLog.setErrMsg(errMsg);
			// adviceException
			accessLog.setAdviceException(adviceException);
			if (method != null) {
				method.setAccessible(isAccessible);
			}

			// 将访问日志保存到mongodb中
			accessLogService.asyncSave(accessLog);
			log.info(accessLog.toJson());
		}
		return object;
	}

	private void putInputParams(String[] paramNames, AccessLog accessLog, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			Class<?> clz = args[i].getClass();
			boolean isPolarisClass = clz.getName().startsWith("com.orion");
			boolean isSimpleClass = ReflectionHelper.isBaseClassOrString(clz) || ReflectionHelper.isCollection(clz)
					|| ReflectionHelper.isMap(clz);
			// 异常处理切面中方法的参数太过于复杂，这里没有必要作为日志打印详细信息，故排除掉
			boolean isNotExceptionClass = !clz.getName().endsWith("Exception");
			if ((isPolarisClass || isSimpleClass) && isNotExceptionClass) {
				accessLog.putInputParam(paramNames[i], ToStringHelper.toJSON(args[i], true));
			} else {
				accessLog.putInputParam(paramNames[i], args[i].toString());
			}
		}
	}

	private String getErrMsg(HttpServletRequest request, Throwable e) {
		String errMsg;
		if (e instanceof ApiException) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			Locale locale;
			if (localeResolver != null) {
				locale = localeResolver.resolveLocale(request);
			} else {
				locale = Locale.getDefault();
			}

			ApiException apiException = (ApiException) e;
			errMsg = messageSource.getMessage(apiException.getErrorKey(), apiException.getArgs(), e.getMessage(),
					locale);
		} else if (e instanceof NullPointerException) {
			errMsg = "NullPointerException";
		} else {
			errMsg = e.getMessage();
		}
		return errMsg;
	}

	private Optional<HttpServletRequest> getHttpServletRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra == null ? Optional.empty() : Optional.of(sra.getRequest());
	}

	private MethodSignature getMethodSignature(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		return (MethodSignature) signature;
	}

}
