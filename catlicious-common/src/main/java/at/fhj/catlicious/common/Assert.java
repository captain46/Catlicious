package at.fhj.catlicious.common;

/**
 * Provides assistance for simple assertions
 *
 * @author bnjm@harmless.ninja - 5/3/17.
 */
public abstract class Assert {

    /**
     * Asserts that the given Object is not null. Throws {@link IllegalArgumentException} if null
     * @param object
     */
    public static void notNull(Object object) {
        if(object == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
    }

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
