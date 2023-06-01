package common.exception;

public class WrongArgumentException extends RuntimeException {

    public WrongArgumentException() {
        super("У команды не долнжо быть аргументов");
    }

    public WrongArgumentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

