package top.bootz.security.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import top.bootz.security.demo.exception.UserNotExistException;

/**
 * @author zhailiang
 *
 */
@ControllerAdvice
public class ExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(UserNotExistException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
		Map<String, Object> result = new HashMap<>();
		result.put("id", ex.getId());
		result.put("message", ex.getMessage());
		return result;
	}

}
