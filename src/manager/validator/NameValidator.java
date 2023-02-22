package manager.validator;

public class NameValidator implements Validatable {

    public static boolean validate(Object value) {
        return value != null && !((String) value).isEmpty();
    }
}
