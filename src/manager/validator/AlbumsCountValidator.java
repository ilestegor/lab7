package manager.validator;

public class AlbumsCountValidator implements Validatable {



    public static boolean validate(Object value)  {
        return (int) value > 0;
    }
}
