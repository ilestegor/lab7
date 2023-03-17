package manager.validator;

/**
 * Contains logic for creationDate validation
 * @author ilestegor
 */
public class CreationDateValidator implements Validatable {
    /**
     * Validates input value, must be not null
     * @param value
     * @return true if value is correct, false if is incorrect
     */
    public static boolean validate(Object value) {
        return value != null;
    }
}
