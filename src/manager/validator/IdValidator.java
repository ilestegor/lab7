package manager.validator;

/**
 * Contains logic for id validation
 * @author ilestegor
 */
public class IdValidator implements Validatable {
    /**
     * Validates input value, must be long > 0
     * @param value
     * @return true if value is correct, false if value is incorrect
     */
    public static boolean validate(Object value) {
        return (long) value > 0;
    }
}
