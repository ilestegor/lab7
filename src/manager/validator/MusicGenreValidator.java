package manager.validator;

public class MusicGenreValidator implements Validatable{
    public static boolean validate(Object value)  {
        return value != null;
    }
}
