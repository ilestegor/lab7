package common.interfaces;

/**
 * Contains logic for printing messages to user
 *
 * @author ilestegor
 */
public interface IPrinter {
    void printNextLine(String message);

    void printThisLine(String message);

    void printError(String errorMessage);
}
