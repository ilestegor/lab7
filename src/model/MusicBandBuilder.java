package model;

import interfaces.IMusicBandBuilder;
import manager.validator.ModelValidator;
import utility.Printer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Builder of MusicBand object using Builder pattern
 */
public class MusicBandBuilder implements IMusicBandBuilder {
    private long id; //must be unique, > 0 and generated automatically
    private String name; //filed can't be null or empty
    private Coordinates coordinates; //field can't be null
    private LocalDateTime creationDate; //file can't be null, generated automatically
    private Integer numberOfParticipants; //filed can't be null, must be > 0
    private int albumsCount; //filed must be > 0
    private Date establishmentDate; //field can't be null
    private MusicGenre genre; //field can't be null
    private Label label; //filed can be null
    private final Printer printer = new Printer();
    private final Scanner scanner = new Scanner(System.in);
    private final ModelValidator modelValidator = new ModelValidator();

    public MusicBandBuilder() {
        super();
    }

    @Override
    public IMusicBandBuilder buildId() {
        Random random = new Random();
        final int upper = 10000000;
        this.id = random.nextLong(upper);
        return this;
    }

    @Override
    public IMusicBandBuilder buildName() {
        var name = "";
        do {
            printer.printThisLine("Введите имя музкальной группы: ");
            name = scanner.nextLine().strip();
            if (!name.isEmpty()) {
                this.name = name;
            } else {
                printer.printNextLine("\nПоле не может быть пустым и не удовлетворяет требованиям! Попробуйте еще раз");
            }
        } while (!modelValidator.validateName(name));
        return this;
    }

    @Override
    public IMusicBandBuilder buildCoordinates() {
        var coordinateXInput = "";
        var coordinateYInput = "";

        Long x = (long) -612;
        float y = 506;
        do {
            try {
                printer.printThisLine("\nВведите координату X типа (int), координата должна быть > -611 и не может быть пропущена: ");
                coordinateXInput = scanner.nextLine().strip();
                if (!coordinateXInput.isEmpty()) {
                    x = Long.parseLong(coordinateXInput);
                }
                if (x <= -611) {
                    printer.printNextLine("Невалидное поле, введите число (int) < 611");
                }
            } catch (NumberFormatException ex) {
                printer.printNextLine("Невалидные данные! Данные должны быть типа int > -611 Попробуйте еще раз");
            }
        } while (!modelValidator.validateCoordinateX(x));
        do {
            try {
                printer.printThisLine("\nВведите координату Y типа (float), координата должна быть <=505 или пропустите этот ввод, нажав enter/return.IMPORTANT(при пропуске, значение становится 0): ");
                coordinateYInput = scanner.nextLine().strip();
                if (coordinateYInput.isEmpty()) {
                    y = 0.0f;
                } else if (Float.parseFloat(coordinateYInput) > 505.0) {
                    printer.printNextLine("Введите число (float) <= 505.0 или пропустите ввод, нажав enter");
                } else {
                    y = Float.parseFloat(coordinateYInput.replace(",", "."));
                }
            } catch (NumberFormatException ex) {
                printer.printNextLine("Невалидные данные! Данные должны быть типа int > -611 Попробуйте еще раз");
            }
            this.coordinates = new Coordinates(x, y);
        } while (!modelValidator.validateCoordinateY(y));
        return this;
    }

    @Override
    public IMusicBandBuilder buildCreationDate() {
        this.creationDate = LocalDate.now().atStartOfDay();
        return this;
    }

    @Override
    public IMusicBandBuilder buildNumberOfParticipants() {
        var numberInput = "";
        Integer numberOfParticipants = 0;
        do {
            try {
                printer.printThisLine("\nВведите количество участников > 0 (int): ");
                numberInput = scanner.nextLine().strip();
                if (!numberInput.isEmpty()) {
                    numberOfParticipants = Integer.parseInt(numberInput);
                    this.numberOfParticipants = numberOfParticipants;
                }
                if (numberOfParticipants <= 0) {
                    printer.printNextLine("Введите число (int) > 0");
                }
            } catch (NumberFormatException ex) {
                printer.printNextLine("Невалидные данные! Попробуйте ввести еще раз");
            }
        } while (!modelValidator.validateNumberOfParticipants(numberOfParticipants));
        return this;
    }

    @Override
    public IMusicBandBuilder buildAlbumsCount() {
        var albumsCountInput = "";
        int albumsCount = 0;
        do {
            try {
                printer.printThisLine("\nВведите количество альбомов (int > 0): ");
                albumsCountInput = scanner.nextLine().strip();
                if (!albumsCountInput.isEmpty()) {
                    albumsCount = Integer.parseInt(albumsCountInput);
                    this.albumsCount = albumsCount;
                }
                if (albumsCount <= 0) {
                    printer.printNextLine("Введите число (int) > 0");
                }
            } catch (NumberFormatException ex) {
                printer.printNextLine("Невалидные данные! Попробуйте еще раз");
            }
        } while (!modelValidator.validateAlbumsCount(albumsCount));
        return this;
    }

    @Override
    public IMusicBandBuilder buildEstablishmentDate() {
        var estDateUserInput = "";
        var estDate = "";
        do {
            printer.printThisLine("\nВведите дату создания группы в формате yyyy-mm-dd: ");
            estDateUserInput = scanner.nextLine().strip();
            try {
                if (!estDateUserInput.isEmpty()) {
                    String[] estDateList = estDateUserInput.split("-");
                    if (((Integer.parseInt(estDateList[0]) > 2023) || (estDateList[0].length() < 4) || (Integer.parseInt(estDateList[0]) < 0))
                            || ((Integer.parseInt(estDateList[1]) > 12) || (Integer.parseInt(estDateList[1]) < 0))
                            || ((Integer.parseInt(estDateList[2]) > 31) || (Integer.parseInt(estDateList[2]) < 0))) {
                        printer.printNextLine("Невалидная дата!");
                    } else {
                        estDate = estDateUserInput;
                    }
                }
            } catch (NumberFormatException ex) {
                printer.printNextLine("Невалидная дата!");
            }
        } while (!modelValidator.validateEstablishmentDate(estDate));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(estDateUserInput);
            this.establishmentDate = date;
        } catch (ParseException e) {
        }
        return this;
    }

    @Override
    public IMusicBandBuilder buildMusicGenre() {
        MusicGenre genre = null;
        var genreInput = "";
        do {
            printer.printThisLine("\nВыберите и введите номер одного из жанров музкальной группы: \n");
            int i = 0;
            for (MusicGenre musicGenre : MusicGenre.values()) {
                System.out.println(++i + ". " + musicGenre);
            }
            try {
                genreInput = scanner.nextLine().strip();
                if (!genreInput.isEmpty()) {
                    if (MusicGenre.valueOfLabel(genreInput) == null) {
                        printer.printNextLine("Такого номера в списке нет! Попробуйте еще раз");
                    } else {
                        genre = MusicGenre.valueOfLabel(genreInput);
                        this.genre = genre;
                    }
                }

            } catch (IllegalArgumentException ex) {
                printer.printNextLine("Невалидные данные! Попробуйте еще раз");
            }
        } while (!modelValidator.validateMusicGenre(genre));
        return this;
    }

    @Override
    public IMusicBandBuilder buildLabel() {
        Label label = null;
        var labelInput = "";
        do {
            printer.printThisLine("\nВведите название лейбла: ");
            labelInput = scanner.nextLine().strip();
            if (!labelInput.isEmpty()) {
                this.label = new Label(labelInput);
            } else {
                this.label = new Label("null");
            }
        } while (!modelValidator.validateLabel(labelInput));
        return this;
    }

    @Override
    public MusicBand build() {
        return new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, albumsCount, establishmentDate, genre, label);
    }
}
