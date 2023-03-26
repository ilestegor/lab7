package manager.validator;

/**
 * Contains logic for fields validation
 *
 * @author ilestegor
 */
public class ModelValidator {

    public boolean validateAlbumsCount(Object value) {
        return (Integer) value > 0;
    }

    public boolean validateCoordinateX(Object value) {
        return value != null && (long) value > -611;
    }

    public boolean validateCoordinateY(Object value) {
        return (float) value <= 505;
    }

    public boolean validateCreationDate(Object value) {
        return value != null;
    }

    public boolean validateEstablishmentDate(Object value) {
        return value != null && value != "";
    }

    public boolean validateId(Object value) {
        return (long) value > 0;
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
        return value != null && (Integer) value > 0;
    }
}
