package manager.validator;

/**
 * Contains logic for coordinate X validation
 *
 * @author ilestegor
 */
public class CoordinateXValidator implements Validatable {
    /**
     * Validates input value, must be not null, long and > -611
     *
     * @param value
     * @return true if value is correct, false if is incorrect
     */
    public static boolean validate(Object value) {
        return value != null && (long) value > -611;
    }
}
