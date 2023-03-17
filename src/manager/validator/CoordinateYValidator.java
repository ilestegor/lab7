package manager.validator;

/**
 * Contains logic for coordinate Y validation
 *
 * @author ilestegor
 */
public class CoordinateYValidator implements Validatable {
    /**
     * Validates input value, must be float <= 505
     *
     * @param value
     * @return true if value is correct, false if is incorrect
     */
    public static boolean validate(Object value) {
        return (float) value <= 505;
    }
}
