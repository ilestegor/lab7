package manager.validator;

/**
 * Contains logic for fields validation
 *
 * @author ilestegor
 */
public class ModelValidator {
    private static final int ZERO = 0;
    private static final Long COORDINATE_X_MIN_VALUE = -611L;
    private static final float COORDINATE_Y_MAX_VALUE = 505.0f;


    public boolean validateAlbumsCount(Object value) {
        return (Integer) value > ZERO;
    }

    public boolean validateCoordinateX(Object value) {
        return value != null && (long) value > COORDINATE_X_MIN_VALUE;
    }

    public boolean validateCoordinateY(Object value) {
        return (float) value <= COORDINATE_Y_MAX_VALUE;
    }

    public boolean validateCreationDate(Object value) {
        return value != null;
    }

    public boolean validateEstablishmentDate(Object value) {
        return value != null && value != "";
    }

    public boolean validateId(Object value) {
        return (long) value > ZERO;
    }

    public boolean validateLabel(Object value) {
        return !String.valueOf(value).matches("[0-9]+");
    }

    public boolean validateMusicGenre(Object value) {
        return value != null;
    }

    public boolean validateName(Object value) {
        return value != null && !((String) value).isEmpty();
    }

    public boolean validateNumberOfParticipants(Object value) {
        return value != null && (Integer) value > ZERO;
    }
}
