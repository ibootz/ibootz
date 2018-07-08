package top.bootz.commons.exception;

/**
 * 
 * @author John
 * @time 2018年6月17日 上午11:28:02
 */

public class SerializationException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public SerializationException() {
        super();
    }

    public SerializationException(final String msg) {
        super(msg);
    }

    public SerializationException(final Throwable cause) {
        super(cause);
    }

    public SerializationException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

}
