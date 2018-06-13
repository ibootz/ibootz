package top.bootz.user.web.aspect;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import top.bootz.commons.constant.ExceptionConstants;
import top.bootz.commons.exception.ApiException;
import top.bootz.commons.exception.BaseRuntimeException;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.dto.RestMessage;

/**
 * 专门用来处理带有RestController和Controller注解的controller层面的异常.
 * 异常匹配顺序是从上到下，匹配到合适的异常处理程序之后就不再向下匹配
 * 
 * @author John
 *
 */
@RestControllerAdvice(annotations = { RestController.class, Controller.class })
public class ExceptionHandleAdvice {

	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandleAdvice.class);

	@Autowired
	private MessageSource messageSource;

	/**
	 * 处理Controller层主动抛出的API异常
	 */
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<RestMessage> handleApiException(ApiException e) {
		String message = messageSource.getMessage(e.getErrorKey(), e.getArgs(), null);
		if (StringUtils.isBlank(message)) {
			message = messageSource.getMessage(ExceptionConstants.API_EXCEPTION, null, null);
		}
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

	/**
	 * 400 - Spring validation Exception - 不符合验证规范
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<RestMessage> handleBindException(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		String messageKey = ExceptionConstants.BAD_REQUEST;
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			messageKey = fieldError.getDefaultMessage();
		}
		String message = messageSource.getMessage(messageKey, null, null);
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		RestMessage.setMoreInfo(e.getMessage());
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

	/**
	 * 400 - Spring validation Exception - 不符合验证规范
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<RestMessage>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<RestMessage> RestMessages = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String message = messageSource.getMessage(fieldError.getDefaultMessage(), null, null);
			RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
			RestMessage.setMoreInfo(e.getMessage());
			RestMessages.add(RestMessage);
		}
		if (CollectionUtils.isEmpty(RestMessages)) {
			String message = messageSource.getMessage(ExceptionConstants.BAD_REQUEST, null, null);
			RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
			RestMessage.setMoreInfo(e.getMessage());
			RestMessages.add(RestMessage);
		}
		return new ResponseEntity<>(RestMessages, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 400 - Bad Request - JSON序列化反序列化失败等异常
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RestMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		String message = messageSource.getMessage(ExceptionConstants.BAD_REQUEST, null, null);
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		RestMessage.setMoreInfo(e.getMessage());
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<RestMessage> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
		String message = messageSource.getMessage(ExceptionConstants.METHOD_NOT_ALLOWED, null, null);
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		RestMessage.setMoreInfo(e.getMessage());
		String[] supportedMethods = e.getSupportedMethods();
		if (supportedMethods != null) {
			response.setHeader("Allow",
					org.springframework.util.StringUtils.arrayToDelimitedString(supportedMethods, ", "));
		}
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<RestMessage> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
			HttpServletResponse response) {
		String message = messageSource.getMessage(ExceptionConstants.CONTENT_TYPE_NOT_SUPPORTED, null, null);
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		RestMessage.setMoreInfo(e.getMessage());
		List<MediaType> mediaTypes = e.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			response.setHeader("Accept", MediaType.toString(mediaTypes));
		}
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

	/**
	 * 500 - 处理其他层抛过来的经过封装的异常，通过在AccessHandleAdvice中拦截封装，可以拦截绝大部分应用内抛出来的异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BaseRuntimeException.class)
	public ResponseEntity<RestMessage> handlePolarisException(BaseRuntimeException e) {
		String message = messageSource.getMessage(ExceptionConstants.APPLICATION_EXCEPTION, null, null);
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		if ("null".equalsIgnoreCase(e.getMessage()) || StringUtils.isBlank(e.getMessage())) {
			RestMessage.setMoreInfo("空指针异常");
		} else {
			RestMessage.setMoreInfo(e.getMessage());
		}
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

	/**
	 * 500 - Internal Server Error 一些其他异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<RestMessage> handleException(Exception e) {
		String message = messageSource.getMessage(ExceptionConstants.UNKNOWN_EXCEPTION, null, null);
		RestMessage RestMessage = JsonHelper.fromJSON(message, RestMessage.class);
		RestMessage.setMoreInfo(e.getMessage());
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<>(RestMessage, HttpStatus.valueOf(RestMessage.getHttpStatus()));
	}

}
