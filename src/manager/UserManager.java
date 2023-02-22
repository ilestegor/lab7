package manager;

import command.Command;

import java.util.HashMap;
import java.util.Scanner;

public class UserManager {
    private static HashMap<String, Command> commandMap;
    private static Scanner scanner;
    private static boolean isInWork;

    static {
        commandMap = CommandManager.getCommandMap();
        scanner = new Scanner(System.in);
        isInWork = true;
    }

    public static void requestCommand(){
        System.out.print("Введите команду: ");
        String line = scanner.nextLine().strip();

        String[] commandAndArgument = line.toLowerCase().split(" ");

        String command = commandAndArgument[0];
            String argument;
            if (commandAndArgument.length == 1){
               argument = null;
            }  else if (commandAndArgument.length == 2){
                argument = commandAndArgument[1];
            } else {
                System.out.println("Нужно ввести команду!");
                return;
            }

        if (commandMap.containsKey(commandAndArgument[0])){
            commandMap.get(commandAndArgument[0]).setArgs(argument);
            commandMap.get(commandAndArgument[0]).execute();
        } else {
            System.out.println("Команды с названием " + commandAndArgument[0] + " не существует! Для уточнения команды воспользуйтесь командой \"help\" ");
        }
    }

    public static boolean isIsInWork() {
        return isInWork;
    }

    public static void setIsInWork(boolean isInWork) {
        UserManager.isInWork = isInWork;
    }
}
