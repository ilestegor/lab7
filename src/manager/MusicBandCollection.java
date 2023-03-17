package manager;

import manager.validator.*;
import model.MusicBand;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Contains tools for operation MusicBand collection
 *
 * @author ilestegor
 */

public class MusicBandCollection {
    private static LocalDateTime creationDate;
    private static MusicBand[] musicBandArray;
    private static final LinkedList<MusicBand> musicBandLinkedList = new LinkedList<>();
    private static String path;

    public MusicBandCollection(MusicBand[] musicBandArray, String path) {
        MusicBandCollection.musicBandArray = musicBandArray;
        creationDate = LocalDateTime.now();
        MusicBandCollection.path = path;
    }

    private static final HashSet<Number> idContainer = new HashSet<>();

    /**
     * Method for validation input values and adding to collection new objects from file
     *
     * @param musicBand
     */
    public static void validateAndAddToCollection(MusicBand musicBand) {
        if (idContainer.contains(musicBand.getId())) {
            System.out.println("Объект с id" + musicBand.getId() + " уже существует! Объект не добавлен в коллекцию");
            return;
        }
        idContainer.add(musicBand.getId());
        if (IdValidator.validate(musicBand.getId()) && NameValidator.validate(musicBand.getName())
                && CoordinateXValidator.validate(musicBand.getCoordinates().getX())
                && CoordinateYValidator.validate(musicBand.getCoordinates().getY()) && CreationDateValidator.validate(musicBand.getCreationDate())
                && NumberOfParticipantsValidator.validate(musicBand.getNumberOfParticipants()) && AlbumsCountValidator.validate(musicBand.getAlbumsCount())
                && EstablishmentDateValidator.validate(musicBand.getEstablishmentDate()) && MusicGenreValidator.validate(musicBand.getGenre()) && LabelValidator.validate(musicBand.getLabel())) {
            musicBandLinkedList.add(musicBand);
        } else {
            System.out.println("Объект с индексом " + musicBand.getId() + " имеет невалидные поля! Объект не загружен в коллекцию");
        }
    }

    /**
     * Method for adding new objects to collection via add command
     *
     * @param musicBand
     */
    public static void addToCollection(MusicBand musicBand) {
        musicBandLinkedList.add(musicBand);
    }

    /**
     * Getting container with ids for all objects in collection
     *
     * @return HashSet with ids
     */
    public static HashSet<Number> getIdContainer() {
        return idContainer;
    }

    /**
     * Getting localDate for collection initialization
     *
     * @return LocalDateTime object with cuurent date
     */
    public static LocalDateTime getLocalDate() {
        return creationDate;
    }

    /**
     * Getting collection
     *
     * @return LinkedList with MusicBand objects
     */
    public static LinkedList<MusicBand> getMusicBandLinkedList() {
        return musicBandLinkedList;
    }
}
