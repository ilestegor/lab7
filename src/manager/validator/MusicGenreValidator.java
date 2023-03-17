package manager.validator;

/**
 * Contains logic for musicGenre validation
 * @author ilestegor
 */
public class MusicGenreValidator implements Validatable {
    /**
     * Validate input value, must be not null
     * @param value
     * @return true if value is correct, false if value is incorrect
     */
    public static boolean validate(Object value) {
        return value != null;
    }
}
