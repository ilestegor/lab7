package manager;

import manager.validator.ModelValidator;
import model.MusicBand;
import parse.YamlReader;
import utility.Printer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Contains tools for operation MusicBand collection
 *
 * @author ilestegor
 */

public class CollectionManager {
    private final LocalDateTime creationDate;
    private final LinkedList<MusicBand> musicBandLinkedList = new LinkedList<>();
    private final ModelValidator modelValidator = new ModelValidator();


    public CollectionManager() {
        creationDate = LocalDateTime.now();
    }

    private static final HashSet<Number> idContainer = new HashSet<>();
    private final Printer printer = new Printer();

    /**
     * Method for validation input values and adding to collection new objects from file
     *
     * @param musicBand
     */
    public void validateAndAddToCollection(MusicBand musicBand) {
        if (idContainer.contains(musicBand.getId())) {
            printer.printNextLine("Объект с id" + musicBand.getId() + " уже существует! Объект не добавлен в коллекцию");
            return;
        }
        idContainer.add(musicBand.getId());
        if (modelValidator.validateId(musicBand.getId()) && modelValidator.validateName(musicBand.getName())
                && modelValidator.validateCoordinateX(musicBand.getCoordinates().getX())
                && modelValidator.validateCoordinateY(musicBand.getCoordinates().getY()) && modelValidator.validateCreationDate(musicBand.getCreationDate())
                && modelValidator.validateNumberOfParticipants(musicBand.getNumberOfParticipants()) && modelValidator.validateAlbumsCount(musicBand.getAlbumsCount())
                && modelValidator.validateEstablishmentDate(musicBand.getEstablishmentDate()) && modelValidator.validateMusicGenre(musicBand.getGenre()) && modelValidator.validateLabel(musicBand.getLabel())) {
            addToCollection(musicBand);
        } else {
            printer.printNextLine("Объект с id " + musicBand.getId() + " имеет невалидные поля! Объект не загружен в коллекцию");
        }
    }

    /**
     * Adds data to collection
     *
     * @param yamlReader
     */
    public void readToCollection(YamlReader yamlReader) {
        try {
            MusicBand[] yaml = yamlReader.read(System.getenv("YamlFile"));
            if (yaml != null) {
                for (MusicBand m : yaml) {
                    validateAndAddToCollection(m);
                }
            }
        } catch (Exception e) {
            printer.printNextLine("Что-то пошло не так во время загрузки коллекции");
        }
    }

    /**
     * Clears collection
     */
    public void clearCollection() {
        musicBandLinkedList.clear();
    }

    /**
     * Removes object from collection by id
     *
     * @param musicBand
     */
    public void removeFromCollection(MusicBand musicBand) {
        if (musicBand != null) {
            musicBandLinkedList.remove(musicBand);
            printer.printNextLine("Объект с id " + musicBand.getId() + " успешно удален!");
        } else printer.printNextLine("Объекта с введенным id не существует");
    }

    /**
     * Finds object by id
     *
     * @param id
     * @return
     */
    public MusicBand findModelById(Integer id) {
        for (MusicBand m : musicBandLinkedList) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Method for adding new objects to collection via add command
     *
     * @param musicBand
     */
    public void addToCollection(MusicBand musicBand) {
        musicBandLinkedList.add(musicBand);
    }

    /**
     * Getting container with ids for all objects in collection
     *
     * @return HashSet with ids
     */
    public HashSet<Number> getIdContainer() {
        return idContainer;
    }

    /**
     * Getting localDate for collection initialization
     *
     * @return LocalDateTime object with cuurent date
     */
    public LocalDateTime getLocalDate() {
        return creationDate;
    }

    /**
     * Getting collection
     *
     * @return LinkedList with MusicBand objects
     */
    public LinkedList<MusicBand> getMusicBandLinkedList() {
        return musicBandLinkedList;
    }
}
