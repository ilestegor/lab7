package manager.validator;

/**
 * Contains logic for name validation
 *
 * @author ilestegor
 */
public class NameValidator implements Validatable {
    /**
     * Validates input value, must be not null and not empty
     *
     * @param value
     * @return true if value is correct, false if value is incorrect
     */
    public static boolean validate(Object value) {
        return value != null && !((String) value).isEmpty();
    }
}
