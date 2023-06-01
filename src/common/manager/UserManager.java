package common.manager;

import common.builder.MusicBandBuilder;
import common.command.Command;
import common.utility.Printer;
import server.model.MusicBand;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Contains tools for getting data from user
 *
 * @author ilestegor
 */
public class UserManager {
    private final HashMap<String, Command> commandMap;
    private final Scanner scanner;
    private static boolean isInWork;
    private LinkedList<String> inputCommand;
    private final Printer printer;
    private final int COMMAND_NAME_POSITION = 0;
    private final int COMMAND_ARGUMENT_POSITION = 1;

    public UserManager() {
        inputCommand = new LinkedList<>();
        commandMap = CommandManager.getClientCommandMap();
        printer = new Printer();
        isInWork = true;
        scanner = new Scanner(System.in);
    }


    /**
     * Gets data from user via Command Line and executes commands
     *
     * @param line
     */
    public String[] requestPlainCommand(String line) {
        String[] commandAndArgument = line.split(" ");
        String command = commandAndArgument[COMMAND_NAME_POSITION];
        String argument;
        if (commandAndArgument.length == 1) {
            return new String[]{command.toLowerCase()};
        } else if (commandAndArgument.length == 2) {
            argument = commandAndArgument[COMMAND_ARGUMENT_POSITION];
            return new String[]{command.toLowerCase(), argument};
        } else {
            return new String[]{};
        }
    }

    /**
     * Requests stuff.command from user with ask to enter stuff.command
     */
    public void requestCommand() {
        try {
            printer.printThisLine("\nВведите команду: ");
            String line = scanner.nextLine().strip().replaceAll("\\s+", " ");
            inputCommand.addLast(line);
        } catch (NoSuchElementException ex) {
            printer.printNextLine("Завершение программы!");
            setIsInWork(false);
        }
    }

    /**
     * Requests data from user for adding new MusicBand object to collection. Used with add stuff.command
     *
     * @return MusicBand object
     */
    public MusicBand requestDataForUserMusicBand() {
        MusicBandBuilder musicBandBuilder = new MusicBandBuilder();
        return musicBandBuilder.buildId().buildName().buildCoordinates().buildCreationDate().buildNumberOfParticipants().buildAlbumsCount().buildEstablishmentDate().buildMusicGenre().buildLabel().build();
    }

    /**
     * Flag for indicating that program is running
     *
     * @return true if program is working, false otherwise
     */
    public boolean isIsInWork() {
        return isInWork;
    }

    /**
     * Method for setting work of program
     *
     * @param inWork
     */

    public static void setIsInWork(boolean inWork) {
        isInWork = inWork;
    }

    public LinkedList<String> getInputCommand() {
        return inputCommand;
    }

    public void setInputCommand(LinkedList<String> inputCommand) {
        this.inputCommand = inputCommand;
    }
}
