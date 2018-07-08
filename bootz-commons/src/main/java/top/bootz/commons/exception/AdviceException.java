package top.bootz.commons.exception;

public class AdviceException extends Exception {

    private static final long serialVersionUID = 5828303901651182015L;

    public AdviceException() {
    }

    public AdviceException(String message) {
        super(message);
    }

    public AdviceException(Throwable cause) {
        super(cause);
    }

    public AdviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
