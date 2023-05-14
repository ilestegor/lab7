package common.exception;

/**
 * Custom common.exception for recursion
 *
 * @author ilestegor
 */
public class RecursionException extends RuntimeException {
    public RecursionException(String message) {
        super(message);
    }
}
