package model;

import manager.validator.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Model of MusicBand. Contains getters/setters for each field of class.
 * Contains some restrictions for filed. Restrictions signed near every field
 *
 * @author ilestegor
 */
public class MusicBand {
    private long id; //must be unique, > 0 and generated automatically
    private String name; //filed can't be null or empty
    private Coordinates coordinates; //field can't be nu;;
    private LocalDateTime creationDate; //file can't be null, generated automatically
    private Integer numberOfParticipants; //filed can't be null, must be > 0
    private int albumsCount; //filed must be > 0
    private Date establishmentDate; //field can't be null
    private MusicGenre genre; //field can't be null
    private Label label; //filed can be null

    public MusicBand() {
    }

    public MusicBand(long id, String name, Coordinates coordinates,
                     LocalDateTime creationDate, Integer numberOfParticipants,
                     int albumsCount, Date establishmentDate,
                     MusicGenre genre, Label label) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.label = label;
    }

    public void setName(String name) {
        if (NameValidator.validate(name)){
            this.name = name;
        }
    }
    public void setCoordinates(Coordinates coordinates) {
       if (CoordinateXValidator.validate(coordinates.getX()) && CoordinateYValidator.validate(coordinates.getY())){
           this.coordinates = coordinates;
       }
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        if (NumberOfParticipantsValidator.validate(numberOfParticipants)){
            this.numberOfParticipants = numberOfParticipants;
        }
    }

    public void setAlbumsCount(int albumsCount) {
        if (AlbumsCountValidator.validate(albumsCount)){
            this.albumsCount = albumsCount;
        }
    }

    public void setEstablishmentDate(Date establishmentDate) {
        if (EstablishmentDateValidator.validate(establishmentDate)){
            this.establishmentDate = establishmentDate;
        }
    }

    public void setGenre(MusicGenre genre) {
        if (MusicGenreValidator.validate(genre)){
            this.genre = genre;
        }
    }

    public void setLabel(Label label) {
        if (LabelValidator.validate(label)){
            this.label = label;
        }
    }

    public void setId(long id) {
        if (IdValidator.validate(id)){
            this.id = id;
        }
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public LocalDateTime getCreationDate() {
        return this.creationDate;
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

    public Label getLabel() {
        return label;
    }

    /**
     * Method for updating MusicBand's instance when using update command
     *
     * @param musicBand
     */
    public void updateElement(MusicBand musicBand) {
        setName(musicBand.getName());
        setCoordinates(musicBand.getCoordinates());
        setNumberOfParticipants(musicBand.getNumberOfParticipants());
        setAlbumsCount(musicBand.getAlbumsCount());
        setEstablishmentDate(musicBand.getEstablishmentDate());
        setGenre(musicBand.getGenre());
        setLabel(musicBand.getLabel());
    }

    @Override
    public String toString() {
        return "---------\n" +
                "id: " + id + "\n" +
                "name: " + name + "\n" +
                "coordinates: " + coordinates + "\n" +
                "creationDate: " + creationDate + "\n" +
                "numberOfParticipants: " + numberOfParticipants + "\n" +
                "albumsCount: " + albumsCount + "\n" +
                "establishmentDate: " + establishmentDate + "\n" +
                "genre: " + genre + "\n" +
                "label: " + label + "\n";
    }
}
