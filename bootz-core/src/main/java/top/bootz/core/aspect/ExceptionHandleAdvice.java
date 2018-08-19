package top.bootz.core.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.RequestContextUtils;

import top.bootz.commons.constant.ExceptionConstants;
import top.bootz.commons.exception.ApiException;
import top.bootz.commons.exception.BaseException;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.message.ErrorMessage;
import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;

/**
 * 专门用来处理带有RestController和Controller注解的controller层面的异常.
 * 异常匹配顺序是从上到下，匹配到合适的异常处理程序之后就不再向下匹配
 * 
 * @author John
 *
 */

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
            message = messageSource.getMessage(ExceptionConstants.ErrorMessageKey.API_EXCEPTION, e.getArgs(), locale);
        }
        ErrorMessage error = JsonHelper.fromJSON(message, ErrorMessage.class);
        error.setMoreInfo(e.getMessage());
        error.setThrowable(e);
        RestMessage restMessage = new RestMessage(MessageStatusEnum.ERROR, null, error);
        return new ResponseEntity<>(restMessage, HttpStatus.valueOf(e.getHttpStatus()));
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
            ErrorMessage error = JsonHelper.fromJSON(message, ErrorMessage.class);
            error.setMoreInfo(e.getMessage());
            error.setThrowable(e);
            RestMessage restMessage = new RestMessage(MessageStatusEnum.ERROR, null, error);
            restMessages.add(restMessage);
        }
        if (CollectionUtils.isEmpty(restMessages)) {
            String message = messageSource.getMessage(ExceptionConstants.ErrorMessageKey.BAD_REQUEST, null, locale);
            ErrorMessage error = JsonHelper.fromJSON(message, ErrorMessage.class);
            error.setMoreInfo(e.getMessage());
            error.setThrowable(e);
            RestMessage restMessage = new RestMessage(MessageStatusEnum.ERROR, null, error);
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
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.TYPE_MISMATCH_EXCEPTION, HttpStatus.BAD_REQUEST,
                e, request);
    }

    /**
     * 400 - Bad Request - JSON序列化反序列化失败等异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestMessage> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e,
            HttpServletRequest request, HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.BAD_REQUEST, HttpStatus.BAD_REQUEST, e, request);
    }

    /**
     * 400 - 缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RestMessage> missingServletRequestParameterExceptionHandler(
            MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.BAD_REQUEST, HttpStatus.BAD_REQUEST, e, request);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestMessage> httpRequestMethodNotSupportedExceptionHandler(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
        String[] supportedMethods = e.getSupportedMethods();
        if (supportedMethods != null) {
            response.setHeader("Allow",
                    org.springframework.util.StringUtils.arrayToDelimitedString(supportedMethods, ", "));
        }

        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.METHOD_NOT_ALLOWED, HttpStatus.BAD_REQUEST, e,
                request);
    }

    /**
     * 406 - 不接受的媒体类型
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<RestMessage> httpMediaTypeNotAcceptableExceptionHandler(HttpMediaTypeNotAcceptableException e,
            HttpServletRequest request, HttpServletResponse response) {
        List<MediaType> mediaTypes = e.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            response.setHeader("Accept", MediaType.toString(mediaTypes));
        }

        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION,
                HttpStatus.NOT_ACCEPTABLE, e, request);
    }

    /**
     * 415 - 不支持的媒体类型
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<RestMessage> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e,
            HttpServletRequest request, HttpServletResponse response) {
        List<MediaType> mediaTypes = e.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            response.setHeader("Accept", MediaType.toString(mediaTypes));
        }

        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.HTTP_MEDIA_TYPE_NOT_SUPPORTED,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE, e, request);
    }

    /**
     * 500 - 空指针异常
     * 
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestMessage> nullPointerExceptionHandler(NullPointerException e, HttpServletRequest request,
            HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.NULL_POINTER_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    /**
     * 500 - 类型转换异常
     * 
     */
    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<RestMessage> classCastExceptionHandler(ClassCastException e, HttpServletRequest request,
            HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.CLASS_CAST_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    /**
     * 500 - IO异常
     * 
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<RestMessage> ioExceptionHandler(IOException e, HttpServletRequest request,
            HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.IO_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, e,
                request);
    }

    /**
     * 500 - 未知方法异常
     * 
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<RestMessage> noSuchMethodExceptionHandler(NoSuchMethodException e, HttpServletRequest request,
            HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.NO_SUCH_METHOD_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    /**
     * 500 - 数组越界异常
     * 
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<RestMessage> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e,
            HttpServletRequest request, HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.INDEX_OUT_OF_BOUNDS_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    /**
     * 500 - 数据库访问异常异常
     * 
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<RestMessage> dataAccessExceptionHandler(DataAccessException e, HttpServletRequest request,
            HttpServletResponse response) {
        return buildResponseEntity(ExceptionConstants.ErrorMessageKey.DATA_ACCESS_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    /**
     * 500 - 处理应用内部自定义的全局异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RestMessage> baseExceptionHandler(BaseException e, HttpServletRequest request,
            HttpServletResponse response) {
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        String message = messageSource.getMessage(ExceptionConstants.ErrorMessageKey.APPLICATION_EXCEPTION, null,
                locale);
        ErrorMessage error = JsonHelper.fromJSON(message, ErrorMessage.class);
        if ("null".equalsIgnoreCase(e.getMessage()) || StringUtils.isBlank(e.getMessage())) {
            error.setMoreInfo("空指针异常");
        } else {
            error.setMoreInfo(e.getMessage());
        }
        error.setThrowable(e);
        RestMessage restMessage = new RestMessage(MessageStatusEnum.ERROR, null, error);
        return new ResponseEntity<>(restMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - Internal Server Error 其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestMessage> exceptionHandler(Exception e, HttpServletRequest request,
            HttpServletResponse response) {
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        String message = messageSource.getMessage(ExceptionConstants.ErrorMessageKey.INTERNAL_SERVER_ERROR, null,
                locale);
        ErrorMessage error = JsonHelper.fromJSON(message, ErrorMessage.class);
        error.setMoreInfo(e.getMessage());
        error.setThrowable(e);
        RestMessage restMessage = new RestMessage(MessageStatusEnum.ERROR, null, error);
        return new ResponseEntity<>(restMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<RestMessage> buildResponseEntity(String errKey, HttpStatus httpStatus, Throwable e,
            HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        String message = messageSource.getMessage(errKey, null, locale);
        ErrorMessage error = JsonHelper.fromJSON(message, ErrorMessage.class);
        error.setMoreInfo(e.getMessage());
        error.setThrowable(e);
        RestMessage restMessage = new RestMessage(MessageStatusEnum.ERROR, null, error);
        return new ResponseEntity<>(restMessage, httpStatus);
    }

}
