package common.exception;

/**
 * Custom common.exception for not valid fields
 *
 * @author ilestegor
 */
public class FiledIsNotCorrectException extends RuntimeException {
    public FiledIsNotCorrectException() {
        super("Некоторые поля не соответсвуют требованиям!");
    }
}
