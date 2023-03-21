package command;

import interfaces.CommandInterface;
import utility.Printer;

/**
 * Abstract class contains basic methods for executing commands
 *
 * @author ilestegor
 */
public abstract class Command implements CommandInterface {
    private final String description;
    private final boolean hasArgs;
    private Object args;

    /**
     * Constructor for Command abstract class
     *
     * @param description
     * @param hasArgs
     */
    public Command(String description, boolean hasArgs) {
        this.description = description;
        this.hasArgs = hasArgs;

    }

    @Override
    public abstract void execute(Printer printer);

    @Override
    public abstract boolean checkArgument(Printer printer, Object inputArgs);

    /**
     * Checks if command has arguments
     *
     * @return true if command has arguments, false if it doesn't
     */
    public boolean isHasArgs() {
        return hasArgs;
    }

    /**
     * Method gets description of command
     *
     * @return description of command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets arguments that were entered by user
     *
     * @return command's arguments
     */
    public Object getArgs() {
        return args;
    }

    /**
     * Method sets arguments for executing command
     *
     * @param args
     */
    public void setArgs(Object args) {
        this.args = args;
    }


}
