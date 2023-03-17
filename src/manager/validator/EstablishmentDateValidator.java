package manager.validator;

/**
 * Contains logic for establishmentDate validation
 *
 * @author ilestegor
 */
public class EstablishmentDateValidator implements Validatable {
    /**
     * Validate input value, must be not null and not empty
     *
     * @param value
     * @return true if value is correct, false if value is incorrect
     */
    public static boolean validate(Object value) {
        return value != null && value != "";
    }
}
