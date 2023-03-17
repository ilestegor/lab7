package manager.validator;

/**
 * Contains logic for lable validation
 *
 * @author ilestegor
 */
public class LabelValidator implements Validatable {
    /**
     * Validates input value
     *
     * @param value
     * @return true if value is correct, false if value is incorrect
     */
    public static boolean validate(Object value) {
        return !String.valueOf(value).matches("[0-9]+");
    }
}
