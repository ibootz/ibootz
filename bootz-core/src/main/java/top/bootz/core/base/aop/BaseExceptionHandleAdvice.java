package top.bootz.core.base.aop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.AppConstants;
import top.bootz.commons.constant.ExceptionConstants;
import top.bootz.commons.exception.ApiException;
import top.bootz.commons.exception.BaseException;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.message.ErrorMessage;
import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;

/**
 * 抽象出一些通用的异常，以供子应用继承
 * <p>
 * 专门用来处理带有RestController和Controller注解的controller层面的异常.
 * 异常匹配顺序是从上到下，匹配到合适的异常处理程序之后就不再向下匹配
 * 
 * @author John
 *
 */

@Slf4j
public class BaseExceptionHandleAdvice {

	protected MessageSource messages;

	public BaseExceptionHandleAdvice(MessageSource messages) {
		this.messages = messages;
	}

	/**
	 * 处理Controller层主动抛出的API异常
	 */
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<RestMessage<Null>> apiExceptionHandler(ApiException e, HttpServletRequest request,
			HttpServletResponse response) {
		String message = messages.getMessage(e.getErrorKey(), e.getArgs(), e.getMessage(),
				AppConstants.AppLocale.getDefault(request));
		if (StringUtils.isBlank(message)) {
			log.warn("errorKey [" + e.getErrorKey() + "] 在消息资源文件中没有找到对应的消息体");
			message = messages.getMessage(ExceptionConstants.ErrorMessageKey.API_EXCEPTION, e.getArgs(), e.getMessage(),
					AppConstants.AppLocale.getDefault(request));
		}
		ErrorMessage error = buildErrorMessage(message, e);
		RestMessage<Null> restMessage = new RestMessage<>(MessageStatusEnum.ERROR, null, error);
		return new ResponseEntity<>(restMessage, HttpStatus.valueOf(e.getHttpStatus()));
	}

	/**
	 * 400 - Spring validation Exception - 不符合验证规范
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<List<RestMessage<Null>>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
		BindingResult bindingResult = e.getBindingResult();
		List<RestMessage<Null>> restMessages = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String message = messages.getMessage(fieldError.getDefaultMessage(), null, e.getMessage(),
					AppConstants.AppLocale.getDefault(request));
			ErrorMessage error = buildErrorMessage(message, e);
			RestMessage<Null> restMessage = new RestMessage<>(MessageStatusEnum.ERROR, null, error);
			restMessages.add(restMessage);
		}
		if (CollectionUtils.isEmpty(restMessages)) {
			String message = messages.getMessage(ExceptionConstants.ErrorMessageKey.BAD_REQUEST, null, e.getMessage(),
					AppConstants.AppLocale.getDefault(request));
			ErrorMessage error = buildErrorMessage(message, e);
			RestMessage<Null> restMessage = new RestMessage<>(MessageStatusEnum.ERROR, null, error);
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
	protected ResponseEntity<RestMessage<Null>> typeMismatchExceptionHandler(TypeMismatchException e,
			HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.TYPE_MISMATCH_EXCEPTION, HttpStatus.BAD_REQUEST,
				request, e);
	}

	/**
	 * 400 - Bad Request - JSON序列化反序列化失败等异常
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<RestMessage<Null>> httpMessageNotReadableExceptionHandler(
			HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.BAD_REQUEST, HttpStatus.BAD_REQUEST, request, e);
	}

	/**
	 * 400 - 缺少参数
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<RestMessage<Null>> missingServletRequestParameterExceptionHandler(
			MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.BAD_REQUEST, HttpStatus.BAD_REQUEST, request, e);
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<RestMessage<Null>> httpRequestMethodNotSupportedExceptionHandler(
			HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
		String[] supportedMethods = e.getSupportedMethods();
		if (supportedMethods != null) {
			response.setHeader("Allow",
					org.springframework.util.StringUtils.arrayToDelimitedString(supportedMethods, ", "));
		}

		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.METHOD_NOT_ALLOWED, HttpStatus.BAD_REQUEST,
				request, e);
	}

	/**
	 * 406 - 不接受的媒体类型
	 */
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	protected ResponseEntity<RestMessage<Null>> httpMediaTypeNotAcceptableExceptionHandler(
			HttpMediaTypeNotAcceptableException e, HttpServletRequest request, HttpServletResponse response) {
		List<MediaType> mediaTypes = e.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			response.setHeader("Accept", MediaType.toString(mediaTypes));
		}

		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION,
				HttpStatus.NOT_ACCEPTABLE, request, e);
	}

	/**
	 * 415 - 不支持的媒体类型
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	protected ResponseEntity<RestMessage<Null>> httpMediaTypeNotSupportedExceptionHandler(
			HttpMediaTypeNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
		List<MediaType> mediaTypes = e.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			response.setHeader("Accept", MediaType.toString(mediaTypes));
		}

		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.HTTP_MEDIA_TYPE_NOT_SUPPORTED,
				HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, e);
	}

	/**
	 * 500 - 空指针异常
	 * 
	 */
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<RestMessage<Null>> nullPointerExceptionHandler(NullPointerException e,
			HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.NULL_POINTER_EXCEPTION,
				HttpStatus.INTERNAL_SERVER_ERROR, request, e);
	}

	/**
	 * 500 - 类型转换异常
	 * 
	 */
	@ExceptionHandler(ClassCastException.class)
	protected ResponseEntity<RestMessage<Null>> classCastExceptionHandler(ClassCastException e,
			HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.CLASS_CAST_EXCEPTION,
				HttpStatus.INTERNAL_SERVER_ERROR, request, e);
	}

	/**
	 * 500 - IO异常
	 * 
	 */
	@ExceptionHandler(IOException.class)
	protected ResponseEntity<RestMessage<Null>> ioExceptionHandler(IOException e, HttpServletRequest request,
			HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.IO_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR,
				request, e);
	}

	/**
	 * 500 - 未知方法异常
	 * 
	 */
	@ExceptionHandler(NoSuchMethodException.class)
	protected ResponseEntity<RestMessage<Null>> noSuchMethodExceptionHandler(NoSuchMethodException e,
			HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.NO_SUCH_METHOD_EXCEPTION,
				HttpStatus.INTERNAL_SERVER_ERROR, request, e);
	}

	/**
	 * 500 - 数组越界异常
	 * 
	 */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	protected ResponseEntity<RestMessage<Null>> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e,
			HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.INDEX_OUT_OF_BOUNDS_EXCEPTION,
				HttpStatus.INTERNAL_SERVER_ERROR, request, e);
	}

	/**
	 * 500 - 数据库访问异常异常
	 * 
	 */
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<RestMessage<Null>> dataAccessExceptionHandler(DataAccessException e,
			HttpServletRequest request, HttpServletResponse response) {
		return buildResponseEntity(ExceptionConstants.ErrorMessageKey.DATA_ACCESS_EXCEPTION,
				HttpStatus.INTERNAL_SERVER_ERROR, request, e);
	}

	/**
	 * 500 - 处理应用内部自定义的全局异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<RestMessage<Null>> baseExceptionHandler(BaseException e, HttpServletRequest request,
			HttpServletResponse response) {
		String message = messages.getMessage(ExceptionConstants.ErrorMessageKey.APPLICATION_EXCEPTION, null,
				e.getMessage(), AppConstants.AppLocale.getDefault(request));
		ErrorMessage error = buildErrorMessage(message, e);
		RestMessage<Null> restMessage = new RestMessage<>(MessageStatusEnum.ERROR, null, error);
		return new ResponseEntity<>(restMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 500 - Internal Server Error 其他未知异常
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<RestMessage<Null>> exceptionHandler(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
		String message = messages.getMessage(ExceptionConstants.ErrorMessageKey.INTERNAL_SERVER_ERROR, null,
				e.getMessage(), AppConstants.AppLocale.getDefault(request));
		ErrorMessage error = buildErrorMessage(message, e);
		RestMessage<Null> restMessage = new RestMessage<>(MessageStatusEnum.ERROR, null, error);
		return new ResponseEntity<>(restMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<RestMessage<Null>> buildResponseEntity(String errKey, HttpStatus httpStatus,
			HttpServletRequest request, Throwable e) {
		String message = messages.getMessage(errKey, null, e.getMessage(), AppConstants.AppLocale.getDefault(request));
		ErrorMessage error = buildErrorMessage(message, e);
		RestMessage<Null> restMessage = new RestMessage<>(MessageStatusEnum.ERROR, null, error);
		return new ResponseEntity<>(restMessage, httpStatus);
	}

	protected ErrorMessage buildErrorMessage(String message, Throwable e) {
		ErrorMessage error = null;
		if (JsonHelper.isJsonStr(message)) {
			error = JsonHelper.fromJSON(message, ErrorMessage.class);
		} else {
			error = new ErrorMessage("0", message);
		}
		if ("null".equalsIgnoreCase(e.getMessage()) || StringUtils.isBlank(e.getMessage())) {
			error.setMoreInfo("空指针异常");
		} else {
			error.setMoreInfo(e.getMessage());
		}
		error.setThrowable(e);
		return error;
	}

}
