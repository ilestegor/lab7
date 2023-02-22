package manager.validator;

public class CoordinateYValidator implements Validatable {

    public static boolean validate(Object value) {
        return (float) value <= 505 && (float) value != 0;
    }
}
