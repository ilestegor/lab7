package utility;

import interfaces.IPrinter;

/**
 * Contains logic for printing messages
 */
public class Printer implements IPrinter {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
