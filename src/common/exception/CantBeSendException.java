package common.exception;

public class CantBeSendException extends RuntimeException{
    public CantBeSendException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
