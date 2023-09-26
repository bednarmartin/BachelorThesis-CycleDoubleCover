package algorithm.exceptions;

/**
 * The exception is thrown if graph is inconsistent.
 */
public class InconsistentGraphException extends Exception {
    public InconsistentGraphException(String text) {
        super(text);
    }
}
