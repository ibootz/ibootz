package top.bootz.security.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.commons.exception.BaseRuntimeException;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserNotExistException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	private String id;

}
