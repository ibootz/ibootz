package top.bootz.core.base.aop;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.AppConstants;
import top.bootz.commons.constant.CommonSecurityConstants;
import top.bootz.commons.constant.ExceptionConstants;
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
@Order(Integer.MAX_VALUE) // 低优先级,让步于子应用更具体的切面操作
@Aspect
@Component
public class BaseAccessHandlerAdvice {

	private static final String CONTROLLER_EXECUTION = "execution(* top.bootz..*Controller.**(..)) "
			+ " || execution(* top.bootz..controller..*.**(..))";

	@Autowired
	private MessageSource messages;

	@Autowired
	private AccessLogService accessLogService;

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
		Object object = null;
		boolean successed = true;
		Date exceptionTime = null;
		AccessLog accessLog = new AccessLog();
		long start = 0L;

		try {
			Optional<HttpServletRequest> requestOpt = getHttpServletRequest();
			if (requestOpt.isPresent()) {
				request = requestOpt.get();
			} else {
				throw new ApiException(ExceptionConstants.ErrorMessageKey.BAD_REQUEST);
			}

			MethodSignature methodSignature = getMethodSignature(joinPoint);
			method = methodSignature.getMethod();
			isAccessible = method.isAccessible();
			method.setAccessible(true);
			String[] paramNames = methodSignature.getParameterNames();
			String returnType = methodSignature.getReturnType().getSimpleName();

			// visitor
			String token = request.getHeader(CommonSecurityConstants.HEADER_AUTH_TOKEN);

			// 获取用户信息，存入日志系统（需要重构，后续从session，或者从token中获取）
			

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
		} catch (Throwable e) {
			successed = false;
			exceptionTime = DateHelper.now();
			
			// errMsg
			accessLog.setErrMsg(getErrMsg(request, e));
			// adviceException
			accessLog.setException(e);
			if (!(e instanceof ApiException)) {
			    log.error(e.getMessage(), e);
			}
			throw e;
		} finally {
			// response
			if (accessLog.isReturned() && object != null) {
				accessLog.setResponse(ToStringHelper.toJSON(object, false));
			}
			// tookMillSeconds
			accessLog.setTookMillSeconds(System.currentTimeMillis() - start);
			// successed
			accessLog.setSuccessed(successed);
			if (!accessLog.isSuccessed() && exceptionTime != null) {
				accessLog.setExceptionTime(DateHelper.date2str(exceptionTime));
			}
			if (method != null) {
				method.setAccessible(isAccessible);
			}
			accessLog.setCreateTime(LocalDateTime.now());

			// 将访问日志保存到mongodb中
			if (accessLogService != null) {
			    accessLogService.asyncSave(accessLog);
			}
			log.info(accessLog.toJson());
		}
		return object;
	}

	private void putInputParams(String[] paramNames, AccessLog accessLog, Object[] args) {
		for (int i = 0; i < args.length; i++) {
		    if (args[i] == null) {
		        accessLog.putInputParam("args_index_" + i, null);
		        continue;
		    }
			Class<?> clz = args[i].getClass();
			boolean isInnerAppClass = clz.getName().startsWith("com.orion");
			boolean isSimpleClass = ReflectionHelper.isBaseClassOrString(clz) || ReflectionHelper.isCollection(clz)
					|| ReflectionHelper.isMap(clz);
			// 异常处理切面中方法的参数太过于复杂，这里没有必要作为日志打印详细信息，故排除掉
			boolean isNotExceptionClass = !clz.getName().endsWith("Exception");
			if ((isInnerAppClass || isSimpleClass) && isNotExceptionClass) {
				accessLog.putInputParam(paramNames[i], ToStringHelper.toJSON(args[i], true));
			} else {
				accessLog.putInputParam(paramNames[i], args[i].toString());
			}
		}
	}

	private String getErrMsg(HttpServletRequest request, Throwable e) {
		String errMsg;
		if (e instanceof ApiException) {
			ApiException apiException = (ApiException) e;
			errMsg = messages.getMessage(apiException.getErrorKey(), apiException.getArgs(), e.getMessage(),
					AppConstants.AppLocale.getDefault(request));
			if (StringUtils.isBlank(errMsg)) {
				errMsg = apiException.getErrorKey();
			}
		} else if (e instanceof NullPointerException) {
			errMsg = "空指针异常";
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
