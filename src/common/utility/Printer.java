package common.utility;

import common.interfaces.IPrinter;

/**
 * Contains logic for printing messages
 */
public class Printer implements IPrinter {
    @Override
    public void printNextLine(String message) {
        System.out.println(message);
    }

    @Override
    public void printThisLine(String message) {
        System.out.print(message);
    }

    @Override
    public void printError(String errorMessage) {
        System.err.println(errorMessage);
    }
}
