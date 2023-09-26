package algorithm.exceptions;

/**
 * The exception is thrown if the circuit is inconsistent.
 */
public class InconsistentCircuitException extends Exception {
    public InconsistentCircuitException(String s) {
        super(s);
    }
}
