package manager;

import command.Command;
import exception.RecursionException;
import model.MusicBand;
import model.MusicBandBuilder;
import utility.Printer;
import utility.RecursionLimiter;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Contains tools for getting data from user
 *
 * @author ilestegor
 */
public class UserManager {
    private static final HashMap<String, Command> commandMap;
    private static final Scanner scanner;
    private static boolean isInWork;
    private static final Printer printer = new Printer();

    static {
        Printer printer = new Printer();
        commandMap = CommandManager.getCommandMap();
        scanner = new Scanner(System.in);
        isInWork = true;
        printer.printNextLine("Приложение запущено!");
        printer.printNextLine("Чтобы ознакомиться c командами введите комануд help");
    }

    /**
     * Requests commands for script from file
     *
     * @param list
     */
    public static void requestCommandForScript(List<String> list) {
        try {
            for (String command : list) {
                command = command.replaceAll("\\s+", " ").trim().strip();
                printer.printNextLine("\nСейчас выполняется команда " + command);
                RecursionLimiter.emerge();
                requestPlainCommand(command);
            }
        } catch (RecursionException ex) {
            printer.printError("\nСкрипт вызывает сам себя! Выход из скрипта");
        }
    }

    /**
     * Gets data from user via Command Line and executes commands
     *
     * @param line
     */
    public static void requestPlainCommand(String line) {
        String[] commandAndArgument = line.split(" ");
        String argument;
        String command = commandAndArgument[0];
        if (commandAndArgument.length == 1) {
            argument = null;
            command = command.toLowerCase();
        } else if (commandAndArgument.length == 2) {
            argument = commandAndArgument[1];
            command = command.toLowerCase();
        } else {
            printer.printNextLine("Команда или аргумент введены неверно!");
            return;
        }
        if (commandMap.containsKey(command)) {
            commandMap.get(command).setArgs(argument);
            commandMap.get(command).execute(new Printer());

        } else {
            printer.printNextLine("Команды с названием " + commandAndArgument[0] + " не существует! Для уточнения команды воспользуйтесь командой \"help\" ");
        }
    }

    /**
     * Requests command from user with ask to enter command
     */
    public static void requestCommand() {
        try {
            printer.printThisLine("\nВведите команду: ");
            String line = scanner.nextLine().strip().replaceAll("\\s+", " ");
            requestPlainCommand(line);
        } catch (NoSuchElementException ex) {
            printer.printNextLine("Завершение программы!");
            setIsInWork(false);
        }
    }

    /**
     * Requests data from user for adding new MusicBand object to collection. Used with add command
     *
     * @return MusicBand object
     */
    public static MusicBand requestDataForUserMusicBand() {
        MusicBandBuilder musicBandBuilder = new MusicBandBuilder();
        return musicBandBuilder.buildId().buildName().buildCoordinates().buildCreationDate().buildNumberOfParticipants().buildAlbumsCount().buildEstablishmentDate().buildMusicGenre().buildLabel().build();
    }

    /**
     * Flag for indicating that program is running
     *
     * @return true if program is working, false otherwise
     */
    public static boolean isIsInWork() {
        return isInWork;
    }

    /**
     * Method for setting work of program
     *
     * @param isInWork
     */
    public static void setIsInWork(boolean isInWork) {
        UserManager.isInWork = isInWork;
    }
}
