package manager.validator;

/**
 * Contains validator for albumsCount
 * @author ilestegor
 */
public class AlbumsCountValidator implements Validatable {

    /**
     * Validates input value, must be int > 0
     * @param value
     * @return true if value is correct, false if is incorrect
     */
    public static boolean validate(Object value) {
        return (Integer) value > 0;
    }
}
