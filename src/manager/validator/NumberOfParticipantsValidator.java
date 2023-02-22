package manager.validator;

public class NumberOfParticipantsValidator implements Validatable {


    public static boolean validate(Object value)  {
        return (Integer) value > 0;
    }

}
