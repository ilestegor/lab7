package server.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Clone of MusicBand for normal output to file in Yaml
 *
 * @author ilestegor
 */
public class MusicBandClone implements Serializable {
    @Serial
    private static final long serialVersionUID = 8524131703524372263L;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    private int albumsCount; //Значение поля должно быть больше 0
    private Date establishmentDate; //Поле не может быть null
    private MusicGenre genre; //Поле не может быть null
    private String label; //Поле может быть null

    public MusicBandClone() {
    }

    public MusicBandClone(MusicBand musicBand) {
        this.id = musicBand.getId();
        this.name = musicBand.getName();
        this.coordinates = musicBand.getCoordinates();
        this.creationDate = String.valueOf(musicBand.getCreationDate());
        this.numberOfParticipants = musicBand.getNumberOfParticipants();
        this.albumsCount = musicBand.getAlbumsCount();
        this.establishmentDate = musicBand.getEstablishmentDate();
        this.genre = musicBand.getGenre();
        this.label = String.valueOf(musicBand.getLabel());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public int getAlbumsCount() {
        return albumsCount;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public String getLabel() {
        return label;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setAlbumsCount(int albumsCount) {
        this.albumsCount = albumsCount;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
