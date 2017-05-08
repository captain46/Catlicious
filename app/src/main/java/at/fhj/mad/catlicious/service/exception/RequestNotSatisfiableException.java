package at.fhj.mad.catlicious.service.exception;

/**
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class RequestNotSatisfiableException extends Exception {
    public RequestNotSatisfiableException() {
        super();
    }

    public RequestNotSatisfiableException(String message) {
        super(message);
    }

    public RequestNotSatisfiableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotSatisfiableException(Throwable cause) {
        super(cause);
    }
}
