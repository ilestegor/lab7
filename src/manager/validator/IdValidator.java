package manager.validator;

public class IdValidator implements Validatable {

    public static boolean validate(Object value) {
        return (long) value > 0;
    }
}
