package manager.validator;

/**
 * Contains logic for numberOfParticipants validation
 *
 * @author ilestegor
 */
public class NumberOfParticipantsValidator implements Validatable {
    /**
     * Validates input value, must be not null, int and > 0
     *
     * @param value
     * @return true if value is correct, false if value is incorrect
     */

    public static boolean validate(Object value) {
        return value != null && (Integer) value > 0;
    }

}
