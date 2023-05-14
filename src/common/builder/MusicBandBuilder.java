package common.builder;

import common.interfaces.IMusicBandBuilder;
import common.utility.Printer;
import common.validators.ModelValidator;
import server.model.Coordinates;
import server.model.Label;
import server.model.MusicBand;
import server.model.MusicGenre;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

/**
 * Builder of MusicBand object using Builder pattern
 */
public class MusicBandBuilder implements IMusicBandBuilder, Serializable {
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Integer numberOfParticipants;
    private int albumsCount;
    private Date establishmentDate;
    private MusicGenre genre;
    private Label label;
    private final Printer printer = new Printer();
    private final Scanner scanner = new Scanner(System.in);
    private final ModelValidator modelValidator = new ModelValidator();
    private static final Long MAX_X_COORDINATE = -612L;
    private static final float MAX_Y_COORDINATE = 506.0f;
    private static final float Y_DEFAULT_VALUE = 0.0f;
    private static final int ZERO_VALUE = 0;
    private static final int DEFAULT_ID = 1; //id = 1 will be reserved id for transporting already created object to server
    // where it will be replaced with new generated one
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final int MAX_YEAR = 2023;
    private static final int MAX_MONTH = 12;
    private static final int MAX_DAY = 31;

    public MusicBandBuilder() {
        super();
    }

    @Override
    public IMusicBandBuilder buildId() {
        id = DEFAULT_ID;
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

        Long x = MAX_X_COORDINATE;
        float y = MAX_Y_COORDINATE;
        do {
            try {
                printer.printThisLine("\nВведите координату X типа (int), координата должна быть > -611 и не может быть пропущена: ");
                coordinateXInput = scanner.nextLine().strip();
                if (!coordinateXInput.isEmpty()) {
                    x = Long.parseLong(coordinateXInput);
                }
                if (x <= MAX_X_COORDINATE) {
                    printer.printNextLine("Невалидное поле, введите число (int) > -611");
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
                    y = Y_DEFAULT_VALUE;
                } else if (Float.parseFloat(coordinateYInput) > MAX_Y_COORDINATE - 1) {
                    printer.printNextLine("Введите число (float) <= 505.0 или пропустите ввод, нажав enter");
                } else {
                    y = Float.parseFloat(coordinateYInput.replace(",", "."));
                }
            } catch (NumberFormatException ex) {
                printer.printNextLine("Невалидные данные! Данные должны быть типа int <= 505 Попробуйте еще раз");
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
        Integer numberOfParticipants = ZERO_VALUE;
        do {
            try {
                printer.printThisLine("\nВведите количество участников > 0 (int): ");
                numberInput = scanner.nextLine().strip();
                if (!numberInput.isEmpty()) {
                    numberOfParticipants = Integer.parseInt(numberInput);
                    this.numberOfParticipants = numberOfParticipants;
                }
                if (numberOfParticipants <= ZERO_VALUE) {
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
        int albumsCount = ZERO_VALUE;
        do {
            try {
                printer.printThisLine("\nВведите количество альбомов (int > 0): ");
                albumsCountInput = scanner.nextLine().strip();
                if (!albumsCountInput.isEmpty()) {
                    albumsCount = Integer.parseInt(albumsCountInput);
                    this.albumsCount = albumsCount;
                }
                if (albumsCount <= ZERO_VALUE) {
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
                    if (((Integer.parseInt(estDateList[YEAR_INDEX]) > MAX_YEAR) || (estDateList[YEAR_INDEX].length() < 4) || (Integer.parseInt(estDateList[YEAR_INDEX]) < ZERO_VALUE))
                            || ((Integer.parseInt(estDateList[MONTH_INDEX]) > MAX_MONTH) || (Integer.parseInt(estDateList[MONTH_INDEX]) < ZERO_VALUE))
                            || ((Integer.parseInt(estDateList[DAY_INDEX]) > MAX_DAY) || (Integer.parseInt(estDateList[DAY_INDEX]) < ZERO_VALUE))) {
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
            this.establishmentDate = format.parse(estDateUserInput);
        } catch (ParseException e) {
            printer.printNextLine("Во время ввода даты что-то пошло не так! Попробуйте еще раз!");
        }
        return this;
    }

    @Override
    public IMusicBandBuilder buildMusicGenre() {
        MusicGenre genre = null;
        var genreInput = "";
        do {
            printer.printThisLine("\nВыберите и введите номер одного из жанров музыкальной группы: \n");
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
