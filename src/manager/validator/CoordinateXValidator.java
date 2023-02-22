package manager.validator;

public class CoordinateXValidator implements Validatable {

    public static boolean validate(Object value) {
        return (long) value > -611 &&  value != null;
    }
}
