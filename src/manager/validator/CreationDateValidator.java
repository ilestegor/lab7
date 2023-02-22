package manager.validator;

public class CreationDateValidator implements Validatable {

    public static boolean validate(Object value)  {
        return value != null;
    }
}
