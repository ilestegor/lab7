package manager;
import manager.validator.*;
import model.MusicBand;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class MusicBandCollection {
    private static LocalDateTime creationDate;
    private static MusicBand[] musicBandArray;
    private static LinkedList<MusicBand> musicBandLinkedList = new LinkedList<>();
     private static String path;

    public MusicBandCollection(MusicBand[] musicBandArray, String path) {
        this.musicBandArray = musicBandArray;
        creationDate = LocalDateTime.now();
        this.path = path;
    }


    public static void validateAndAddToCollection(MusicBand musicBand){
            if (IdValidator.validate(musicBand.getId()) && NameValidator.validate(musicBand.getName()) && CoordinateXValidator.validate(musicBand.getCoordinates().getX())
            && CoordinateYValidator.validate(musicBand.getCoordinates().getY()) && CreationDateValidator.validate(musicBand.getCreationDate())
            && NumberOfParticipantsValidator.validate(musicBand.getNumberOfParticipants()) && NumberOfParticipantsValidator.validate(musicBand.getNumberOfParticipants())
            && EstablishmentDateValidator.validate(musicBand.getEstablishmentDate()) && MusicGenreValidator.validate(musicBand.getGenre()) && LabelValidator.validate(musicBand.getLabel())){
                musicBandLinkedList.add(musicBand);
            } else {
                System.out.println("Объект с индексом " + musicBand.getId() + " имеет невалидные поля! Объект не загружен в коллекцию");
            }
    }

    public static LocalDateTime getLocalDate() {
        return creationDate;
    }

    public MusicBand[] getMusicBandArray() {
        return musicBandArray;
    }

    public static LinkedList<MusicBand> getMusicBandLinkedList() {
        return musicBandLinkedList;
    }
}
