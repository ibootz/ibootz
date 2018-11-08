package top.bootz.security.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.commons.exception.BaseRuntimeException;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserNotFoundException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    private final String id;

}
