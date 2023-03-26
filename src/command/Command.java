package command;

import interfaces.CommandInterface;
import manager.CollectionManager;
import utility.Printer;

/**
 * Abstract class contains basic methods for executing commands
 *
 * @author ilestegor
 */
public abstract class Command implements CommandInterface {
    private final String description;

    private Object args;
    private final CollectionManager collectionManager;


    /**
     * Constructor for Command abstract class
     *
     * @param description
     * @param collectionManager
     */
    public Command(String description, CollectionManager collectionManager) {
        this.description = description;
        this.collectionManager = collectionManager;
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

    public CollectionManager getMusicBandCollectionManager() {
        return collectionManager;
    }
}
