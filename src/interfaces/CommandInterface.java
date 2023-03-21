package interfaces;

import utility.Printer;

/**
 * Contains methods for command execution and argument checking
 *
 * @author ilestegor
 */
public interface CommandInterface {
    /**
     * Executes command
     */
    void execute(Printer printer);

    /**
     * Validate arguments
     *
     * @param inputArgs
     * @return
     */

    boolean checkArgument(Printer printer, Object inputArgs);
}
