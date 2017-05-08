package at.fhj.mad.catlicious.service.exception;

/**
 * @author bnjm@harmless.ninja - 5/8/17.
 */
public class ContentProviderFileNotFoundException extends RuntimeException {
    public ContentProviderFileNotFoundException() {
        super();
    }

    public ContentProviderFileNotFoundException(String message) {
        super(message);
    }

    public ContentProviderFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentProviderFileNotFoundException(Throwable cause) {
        super(cause);
    }
}
