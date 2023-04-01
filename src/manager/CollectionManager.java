package manager;

import manager.validator.ModelValidator;
import model.MusicBand;
import parse.YamlReader;
import utility.Printer;

import java.io.FileNotFoundException;
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
    public boolean validateAndAddToCollection(MusicBand musicBand) {
        if (idContainer.contains(musicBand.getId())) {
            musicBandLinkedList.remove(musicBand);
            printer.printNextLine("Объект с id" + musicBand.getId() + " уже существует! Объект не добавлен в коллекцию");
            return false;
        }
        idContainer.add(musicBand.getId());
        if (modelValidator.validateId(musicBand.getId()) && modelValidator.validateName(musicBand.getName())
                && modelValidator.validateCoordinateX(musicBand.getCoordinates().getX())
                && modelValidator.validateCoordinateY(musicBand.getCoordinates().getY()) && modelValidator.validateCreationDate(musicBand.getCreationDate())
                && modelValidator.validateNumberOfParticipants(musicBand.getNumberOfParticipants()) && modelValidator.validateAlbumsCount(musicBand.getAlbumsCount())
                && modelValidator.validateEstablishmentDate(musicBand.getEstablishmentDate()) && modelValidator.validateMusicGenre(musicBand.getGenre()) && modelValidator.validateLabel(musicBand.getLabel())) {
            musicBandLinkedList.add(musicBand);
            return true;
        } else {
            printer.printNextLine("Объект с id " + musicBand.getId() + " имеет невалидные поля! Объект не загружен в коллекцию");
            return false;
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
        } catch (FileNotFoundException e) {
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

    public void updateElementInCollection(MusicBand musicBand, Integer userInput){
        if (findModelById(userInput) != null){
            musicBand.updateElement(UserManager.requestDataForUserMusicBand());
        } else printer.printNextLine("Объекта с id " + userInput + " нет в коллекции!");
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
     * @return LocalDateTime object with current date
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
