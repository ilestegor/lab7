package server.model;

import common.exception.FiledIsNotCorrectException;
import common.validators.ModelValidator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Model of MusicBand. Contains getters/setters for each field of class.
 * Contains some restrictions for filed. Restrictions signed near every field
 *
 * @author ilestegor
 */
public class MusicBand implements Serializable {
    @Serial
    private static final long serialVersionUID = 8855024745871880934L;
    private long id; //must be unique, > 0 and generated automatically
    private String name; //filed can't be null or empty
    private Coordinates coordinates; //field can't be null
    private LocalDateTime creationDate; //file can't be null, generated automatically
    private Integer numberOfParticipants; //filed can't be null, must be > 0
    private int albumsCount; //filed must be > 0
    private Date establishmentDate; //field can't be null
    private MusicGenre genre; //field can't be null
    private Label label; //filed can be null
    private final ModelValidator modelValidator = new ModelValidator();

    public MusicBand() {
    }

    public MusicBand(long id, String name, Coordinates coordinates,
                     LocalDateTime creationDate, Integer numberOfParticipants,
                     int albumsCount, Date establishmentDate,
                     MusicGenre genre, Label label) {
        this.setId(id);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setCreationDate(creationDate);
        this.setNumberOfParticipants(numberOfParticipants);
        this.setAlbumsCount(albumsCount);
        this.setEstablishmentDate(establishmentDate);
        this.setGenre(genre);
        this.setLabel(label);
    }

    public void setName(String name) {
        if (modelValidator.validateName(name)) {
            this.name = name;
        } else throw new FiledIsNotCorrectException();
    }

    public void setCoordinates(Coordinates coordinates) {
        if (modelValidator.validateCoordinateX(coordinates.getX()) && modelValidator.validateCoordinateY(coordinates.getY())) {
            this.coordinates = coordinates;
        } else throw new FiledIsNotCorrectException();
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        if (modelValidator.validateNumberOfParticipants(numberOfParticipants)) {
            this.numberOfParticipants = numberOfParticipants;
        } else throw new FiledIsNotCorrectException();
    }

    public void setAlbumsCount(int albumsCount) {
        if (modelValidator.validateAlbumsCount(albumsCount)) {
            this.albumsCount = albumsCount;
        } else throw new FiledIsNotCorrectException();
    }

    public void setEstablishmentDate(Date establishmentDate) {
        if (modelValidator.validateEstablishmentDate(establishmentDate)) {
            this.establishmentDate = establishmentDate;
        } else throw new FiledIsNotCorrectException();
    }

    public void setGenre(MusicGenre genre) {
        if (modelValidator.validateMusicGenre(genre)) {
            this.genre = genre;
        } else throw new FiledIsNotCorrectException();
    }

    public void setLabel(Label label) {
        if (modelValidator.validateLabel(label)) {
            this.label = label;
        } else throw new FiledIsNotCorrectException();
    }

    public void setId(long id) {
        if (modelValidator.validateId(id)) {
            this.id = id;
        } else throw new FiledIsNotCorrectException();
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (modelValidator.validateCreationDate(creationDate)) {
            this.creationDate = creationDate;
        } else throw new FiledIsNotCorrectException();
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
     * Method for updating MusicBand's instance when using update stuff.command
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
