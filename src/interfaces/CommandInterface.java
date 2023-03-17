package interfaces;

/**
 * Contains methods for command execution and argument checking
 * @author ilestegor
 */
public interface CommandInterface {
    /**
     * Executes command
     */
    void execute();

    /**
     * Validate arguments
     * @param inputArgs
     * @return
     */

    boolean checkArgument(Object inputArgs);
}
