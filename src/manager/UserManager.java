package manager;

import command.Command;
import manager.validator.*;
import model.Coordinates;
import model.Label;
import model.MusicBand;
import model.MusicGenre;
import utility.RecursionException;
import utility.RecursionLimiter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Contains tools for getting data from user
 * @author ilestegor
 */
public class UserManager {
    private static final HashMap<String, Command> commandMap;
    private static final Scanner scanner;
    private static boolean isInWork;

    static {
        commandMap = CommandManager.getCommandMap();
        scanner = new Scanner(System.in);
        isInWork = true;
        System.out.println("Приложение запущено!");
        System.out.println("Чтобы ознакомиться c командами введите комануд help");
    }

    /**
     * Requests commands for script from file
     * @param list
     */
    public static void requestCommandForScript(List<String> list) {
        try {
            for (String command : list) {
                command = command.replaceAll("\\s+", " ").trim().strip();
                System.out.println("\nСейчас выполняется команда " + command);
                RecursionLimiter.emerge();
                requestPlainCommand(command);
            }
        } catch (StackOverflowError | RecursionException ex) {
            System.err.println("\nСкрипт вызывает сам себя! Выход из скрипта");
        }
    }

    /**
     * Gets data from user via Command Line and executes commands
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
            System.out.println("Нужно ввести команду!");
            return;
        }
        if (commandMap.containsKey(command)) {
            commandMap.get(command).setArgs(argument);
            commandMap.get(command).execute();
        } else {
            System.out.println("Команды с названием " + commandAndArgument[0] + " не существует! Для уточнения команды воспользуйтесь командой \"help\" ");
        }
    }

    /**
     * Requests command from user with ask to enter command
     */
    public static void requestCommand() {
        try {
            System.out.print("\nВведите команду: ");
            String line = scanner.nextLine().strip().replaceAll("\\s+", " ");
            requestPlainCommand(line);
        } catch (NoSuchElementException ex){
            System.out.println("Завершение программы!");
            setIsInWork(false);
        }
    }

    /**
     * Requests data from user for adding new MusicBand object to collection. Used with add command
     * @param musicBand
     * @return MusicBand object
     */
    public static MusicBand requestDataForUserMusicBand(MusicBand musicBand) {
        var userInput = "";
        Random random = new Random();
        int upper = 10000000;
        musicBand.setId(random.nextInt(upper));
        try {
            String name = "";
            do {
                System.out.print("Введите имя музкальной группы: ");
                userInput = scanner.nextLine().strip();
                if (!checkIfEmpty(userInput)) {
                    name = userInput;
                }
            } while (!NameValidator.validate(name));
            musicBand.setName(name);

            Long x = null;
            do {
                try {
                    System.out.print("\nВведите координату X типа (int), координата должна быть > -611 и не может быть пропущена: ");
                    userInput = scanner.nextLine().strip();
                    if (!userInput.isEmpty()) {
                        x = Long.parseLong(userInput);
                    }
                    if (Long.parseLong(userInput) < -611) {
                        System.out.println("Невалидное поле, введите число (int) < 611");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Невалидные данные! Данные должны быть типа int > -611 Попробуйте еще раз");
                }
            } while (!CoordinateXValidator.validate(x));


            Float y = 506f;
            do {
                try {
                    System.out.print("\nВведите координату Y типа (float), координата должна быть <=505 или пропустите этот ввод, нажав enter/return: ");
                    userInput = scanner.nextLine().strip();
                    if (userInput.isEmpty()) {
                        y = 0.0f;
                    } else if (Float.parseFloat(userInput) > 505.0) {
                        System.out.println("Введите число (float) <= 505.0");
                    }
                    if (!userInput.isEmpty()) {
                        y = Float.parseFloat(userInput.replace(",", "."));
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Введите число (float) <= 505.0 или пропустите этот ввод, нажав enetr/return");
                }
            } while (!CoordinateYValidator.validate(Float.parseFloat(String.valueOf(y))));
            musicBand.setCoordinates(new Coordinates(x, y));
            musicBand.setCreationDate(LocalDate.now().atStartOfDay());

            Integer numberOfParticipants = 0;
            do {
                try {
                    System.out.print("\nВведите количество участников > 0 (int): ");
                    userInput = scanner.nextLine().strip();
                    if (!checkIfEmpty(userInput)) {
                        numberOfParticipants = Integer.parseInt(userInput);
                    }
                    if (Integer.parseInt(userInput) <= 0) {
                        System.out.println("Введите число (int) > 0");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Невалидные данные! Попробуйте ввести еще раз");
                }
            } while (!NumberOfParticipantsValidator.validate(numberOfParticipants));
            musicBand.setNumberOfParticipants(numberOfParticipants);


            int albumsCount = 0;
            do {
                try {
                    System.out.print("\nВведите количество альбомов (int > 0): ");
                    userInput = scanner.nextLine().strip();
                    if (!checkIfEmpty(userInput)) {
                        albumsCount = Integer.parseInt(userInput);
                    }
                    if (Integer.parseInt(userInput) <= 0) {
                        System.out.println("Введите число (int) > 0");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Невалидные данные! Попробуйте еще раз");
                }
            } while (!AlbumsCountValidator.validate(albumsCount));
            musicBand.setAlbumsCount(albumsCount);

        } catch (NumberFormatException ex) {
            System.out.println("Поле не может быть строкой или пустым!");
        }

        String establishmentDate = "";
        do {
            System.out.print("\nВведите дату создания группы в формате yyyy-mm-dd: ");
            userInput = scanner.nextLine().strip();
            try {
                if (!checkIfEmpty(userInput)) {
                    String[] estDate = userInput.split("-");
                    if ((Integer.parseInt(estDate[0]) > 2023 || estDate[0].length() < 4 || Integer.parseInt(estDate[0]) < 0)
                            || (Integer.parseInt(estDate[1]) > 12 || Integer.parseInt(estDate[1]) < 0)
                            || (Integer.parseInt(estDate[2]) > 31 || Integer.parseInt(estDate[2]) < 0)) {
                        System.out.println("Невалидная дата!");
                    } else {
                        establishmentDate = userInput;
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Невалидная дата!");
            }
        } while (!EstablishmentDateValidator.validate(establishmentDate));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(establishmentDate);
            musicBand.setEstablishmentDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        MusicGenre genre = null;
        do {
            System.out.print("\nВыберите и введите номер одного из жанров музкальной группы: \n");
            int i = 0;
            for (MusicGenre musicGenre : MusicGenre.values()) {
                System.out.println(++i + ". " + musicGenre);
            }
            try {
                userInput = scanner.nextLine().strip();
                Integer.parseInt(userInput);
                if (!checkIfEmpty(userInput)) {
                    if (MusicGenre.valueOfLabel(userInput) == null) {
                        System.out.println("Такого номера в списке нет! Попробуйте еще раз");
                    } else {
                        genre = MusicGenre.valueOfLabel(userInput);
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Введите номер (число), чтобы выбрать жанр!");
            } catch (IllegalArgumentException ex) {
                System.out.println("Невалидные данные! Попробуйте еще раз");
            }
        } while (!MusicGenreValidator.validate(genre));
        musicBand.setGenre(genre);

        Label label = null;
        do {
            System.out.print("\nВведите название лейбла: ");
            userInput = scanner.nextLine().strip();
            if (!userInput.isEmpty()) {
                label = new Label(userInput);
            } else {
                label = new Label("null");
            }
        } while (!LabelValidator.validate(label));
        musicBand.setLabel(label);
        return musicBand;
    }

    /**
     * Method checks if user input is empty
     * @param userInput
     * @return true if user input is empty, false if is not empty
     */
    public static boolean checkIfEmpty(String userInput) {
        if (userInput.isEmpty()) {
            System.out.println("Поле не может быть пустым и не удовлетворяет требованиям! Попробуйте еще раз");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Flag for indicating that program is running
     * @return true if program is working, false otherwise
     */
    public static boolean isIsInWork() {
        return isInWork;
    }

    /**
     * Method for setting work of program
     * @param isInWork
     */
    public static void setIsInWork(boolean isInWork) {
        UserManager.isInWork = isInWork;
    }

}
