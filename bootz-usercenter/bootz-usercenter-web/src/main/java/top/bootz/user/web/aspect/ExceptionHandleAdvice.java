package top.bootz.user.web.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.ExceptionConstants;
import top.bootz.commons.exception.ApiException;
import top.bootz.commons.exception.BaseException;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.dto.RestMessage;

/**
 * 专门用来处理带有RestController和Controller注解的controller层面的异常.
 * 异常匹配顺序是从上到下，匹配到合适的异常处理程序之后就不再向下匹配
 * 
 * @author John
 *
 */
@Slf4j
@RestControllerAdvice(annotations = { RestController.class, Controller.class })
public class ExceptionHandleAdvice {

	@Autowired
	private MessageSource messageSource;

	/**
	 * 处理Controller层主动抛出的API异常
	 */
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<RestMessage> apiExceptionHandler(ApiException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(e.getErrorKey(), e.getArgs(), locale);
		if (StringUtils.isBlank(message)) {
			message = messageSource.getMessage(ExceptionConstants.API_EXCEPTION, null, null);
		}
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 空指针异常
	 * 
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<RestMessage> nullPointerExceptionHandler(NullPointerException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.NULL_POINTER_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 类型转换异常
	 * 
	 */
	@ExceptionHandler(ClassCastException.class)
	public ResponseEntity<RestMessage> classCastExceptionHandler(ClassCastException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.CLASS_CAST_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * IO异常
	 * 
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<RestMessage> ioExceptionHandler(IOException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.IO_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 未知方法异常
	 * 
	 */
	@ExceptionHandler(NoSuchMethodException.class)
	public ResponseEntity<RestMessage> noSuchMethodExceptionHandler(NoSuchMethodException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.NO_SUCH_METHOD_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 数组越界异常
	 * 
	 */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<RestMessage> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e,
			HttpServletRequest request, HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.INDEX_OUT_OF_BOUNDS_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 400 - Spring validation Exception - 不符合验证规范
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<RestMessage> bindExceptionHandler(BindException e, HttpServletRequest request,
			HttpServletResponse response) {
		BindingResult bindingResult = e.getBindingResult();
		String messageKey = ExceptionConstants.BAD_REQUEST;
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			messageKey = fieldError.getDefaultMessage();
		}
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(messageKey, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 400 - Spring validation Exception - 不符合验证规范
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<RestMessage>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,
			HttpServletRequest request, HttpServletResponse response) {
		BindingResult bindingResult = e.getBindingResult();
		List<RestMessage> restMessages = new ArrayList<>();
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String message = messageSource.getMessage(fieldError.getDefaultMessage(), null, locale);
			RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
			restMessage.setMoreInfo(e.getMessage());
			restMessage.setThrowable(e);
			restMessages.add(restMessage);
		}
		if (CollectionUtils.isEmpty(restMessages)) {
			String message = messageSource.getMessage(ExceptionConstants.BAD_REQUEST, null, locale);
			RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
			restMessage.setMoreInfo(e.getMessage());
			restMessage.setThrowable(e);
			restMessages.add(restMessage);
		}
		return new ResponseEntity<>(restMessages, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 400 - Spring validation Exception - 不符合验证规范
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<RestMessage> typeMismatchExceptionHandler(TypeMismatchException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.TYPE_MISMATCH_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 400 - Bad Request - JSON序列化反序列化失败等异常
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RestMessage> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e,
			HttpServletRequest request, HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.BAD_REQUEST, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 400 - 缺少参数
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<RestMessage> missingServletRequestParameterExceptionHandler(
			MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.BAD_REQUEST, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<RestMessage> httpRequestMethodNotSupportedExceptionHandler(
			HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.METHOD_NOT_ALLOWED, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		String[] supportedMethods = e.getSupportedMethods();
		if (supportedMethods != null) {
			response.setHeader("Allow",
					org.springframework.util.StringUtils.arrayToDelimitedString(supportedMethods, ", "));
		}
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 406 - 不接受的媒体类型
	 */
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<RestMessage> httpMediaTypeNotAcceptableExceptionHandler(HttpMediaTypeNotAcceptableException e,
			HttpServletRequest request, HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION, null,
				locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 415 - 不支持的媒体类型
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<RestMessage> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e,
			HttpServletRequest request, HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.HTTP_MEDIA_TYPE_NOT_SUPPORTED, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		List<MediaType> mediaTypes = e.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			response.setHeader("Accept", MediaType.toString(mediaTypes));
		}
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 500 - 处理其他层抛过来的经过封装的异常，通过在AccessHandleAdvice中拦截封装，可以拦截绝大部分应用内抛出来的异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<RestMessage> baseExceptionHandler(BaseException e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.APPLICATION_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		if ("null".equalsIgnoreCase(e.getMessage()) || StringUtils.isBlank(e.getMessage())) {
			restMessage.setMoreInfo("空指针异常");
		} else {
			restMessage.setMoreInfo(e.getMessage());
		}
		restMessage.setThrowable(e);
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

	/**
	 * 500 - Internal Server Error 其他未知异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<RestMessage> exceptionHandler(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage(ExceptionConstants.UNKNOWN_EXCEPTION, null, locale);
		RestMessage restMessage = JsonHelper.fromJSON(message, RestMessage.class);
		restMessage.setMoreInfo(e.getMessage());
		restMessage.setThrowable(e);
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(restMessage.getHttpStatus()));
	}

}
