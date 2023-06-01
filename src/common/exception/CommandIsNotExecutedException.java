package common.exception;

public class CommandIsNotExecutedException extends RuntimeException {


    public CommandIsNotExecutedException() {
        super("Команда не выполнена!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
