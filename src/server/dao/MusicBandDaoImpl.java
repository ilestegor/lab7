package server.dao;

import server.MainServerApp;
import server.model.Coordinates;
import server.model.Label;
import server.model.MusicBand;
import server.model.MusicGenre;
import server.utility.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MusicBandDaoImpl implements DAO<MusicBand> {
    private final Connection connection;
    private static final int SUCCESSFUL_CODE = 0;
    private static final int NAME_POSITION = 1;
    private static final int COORDINATE_X_POSITION = 1;
    private static final int LABEL_NAME_POSITION = 1;
    private static final int COORDINATES_ID_POSITION = 1;
    private static final int LABEL_ID_POSITION = 1;
    private static final int CREATOR_ID_POSITION_IN_DELETE_ALL = 1;
    private static final int MUSICBAND_ID_POSITION = 1;
    private static final int COORDINATE_Y_POSITION = 2;
    private static final int MUSICBAND_COORDINATES_POSITION = 2;
    private static final int MUSICBAND_CREATION_DATE_POSITION = 3;
    private static final int MUSICBAND_NUMBER_OF_PARTICIPANTS_POSITION = 4;
    private static final int MUSICBAND_ALBUMS_COUNT_POSITION = 5;
    private static final int MUSICBAND_ESTABLISHMENT_DATE_POSITION = 6;
    private static final int MUSICBAND_GENRE_POSITION = 7;
    private static final int MUSICBAND_LABEL_ID_POSITION = 8;
    private static final int MUSICBAND_CREATOR_ID_POSITION = 9;

    public MusicBandDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(MusicBand musicBand) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_MUSICBAND.getQuery())) {
            preparedStatement.setString(NAME_POSITION, musicBand.getName());
            preparedStatement.setInt(MUSICBAND_COORDINATES_POSITION, createCoordinates(musicBand.getCoordinates(), musicBand));
            preparedStatement.setString(MUSICBAND_CREATION_DATE_POSITION, musicBand.getCreationDate().toString());
            preparedStatement.setInt(MUSICBAND_NUMBER_OF_PARTICIPANTS_POSITION, musicBand.getNumberOfParticipants());
            preparedStatement.setInt(MUSICBAND_ALBUMS_COUNT_POSITION, musicBand.getAlbumsCount());
            preparedStatement.setString(MUSICBAND_ESTABLISHMENT_DATE_POSITION, musicBand.getEstablishmentDate().toString());
            preparedStatement.setString(MUSICBAND_GENRE_POSITION, musicBand.getGenre().toString());
            preparedStatement.setInt(MUSICBAND_LABEL_ID_POSITION, createLabel(musicBand.getLabel(), musicBand));
            preparedStatement.setInt(MUSICBAND_CREATOR_ID_POSITION, musicBand.getCreatorId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            resultSet.close();
            return id;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    private int createCoordinates(Coordinates coordinates, MusicBand musicBand) {
        final int creatorIdPosition = 3;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_MUSICBAND_COORDINATES.getQuery())) {
            preparedStatement.setLong(COORDINATE_X_POSITION, coordinates.getX());
            preparedStatement.setFloat(COORDINATE_Y_POSITION, coordinates.getX());
            preparedStatement.setInt(creatorIdPosition, musicBand.getCreatorId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            resultSet.close();
            return id;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    private int createLabel(Label label, MusicBand musicBand) {
        final int creatorIdPosition = 2;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_MUSICBAND_LABEL.getQuery())) {
            preparedStatement.setString(LABEL_NAME_POSITION, label.getLabel());
            preparedStatement.setInt(creatorIdPosition, musicBand.getCreatorId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            resultSet.close();
            return id;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    @Override
    public int update(MusicBand musicBand) {
        final int numberOfParticipantsPosition = 2;
        final int albumsCountPosition = 3;
        final int establishmentDatePosition = 4;
        final int genrePosition = 5;
        final int musicBandIdPosition = 6;
        final int creatorIdPosition = 7;
        try (
                PreparedStatement updateMusicBand = connection.prepareStatement(Queries.UPDATE_MUSICBAND.getQuery())
        ) {
            updateMusicBand.setString(NAME_POSITION, musicBand.getName());
            updateMusicBand.setInt(numberOfParticipantsPosition, musicBand.getNumberOfParticipants());
            updateMusicBand.setInt(albumsCountPosition, musicBand.getAlbumsCount());
            updateMusicBand.setString(establishmentDatePosition, musicBand.getEstablishmentDate().toString());
            updateMusicBand.setString(genrePosition, musicBand.getGenre().toString());
            updateMusicBand.setLong(musicBandIdPosition, musicBand.getId());
            updateMusicBand.setInt(creatorIdPosition, musicBand.getCreatorId());
            updateMusicBand.executeUpdate();
            updateCoordinates(musicBand.getCoordinates(), musicBand);
            updateLabel(musicBand.getLabel(), musicBand);
            return SUCCESSFUL_CODE;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    private int updateCoordinates(Coordinates coordinates, MusicBand musicBand) {
        int coordinatesId = findCoordinatesId(musicBand);
        final int coordinatesIdPosition = 3;
        final int creatorIdPosition = 4;
        try (PreparedStatement updateCoordinatesStatement = connection.prepareStatement(Queries.UPDATE_COORDINATES.getQuery())) {
            updateCoordinatesStatement.setLong(COORDINATE_X_POSITION, coordinates.getX());
            updateCoordinatesStatement.setFloat(COORDINATE_Y_POSITION, coordinates.getY());
            updateCoordinatesStatement.setInt(coordinatesIdPosition, coordinatesId);
            updateCoordinatesStatement.setInt(creatorIdPosition, musicBand.getCreatorId());
            updateCoordinatesStatement.executeUpdate();
            return SUCCESSFUL_CODE;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    private int updateLabel(Label label, MusicBand musicBand) {
        int labelId = findLabelById(musicBand);
        final int labelIdPosition = 2;
        final int creatorIdPosition = 3;
        try (PreparedStatement updateLabel = connection.prepareStatement(Queries.UPDATE_LABEL.getQuery())) {
            updateLabel.setString(LABEL_NAME_POSITION, label.getLabel());
            updateLabel.setInt(labelIdPosition, labelId);
            updateLabel.setInt(creatorIdPosition, musicBand.getCreatorId());
            updateLabel.executeUpdate();
            return SUCCESSFUL_CODE;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    @Override
    public int deleteAll(MusicBand musicBand) {
        try (
                PreparedStatement deleteMusicBandStatement = connection.prepareStatement(Queries.DELETE_MUSICBAND.getQuery());
                PreparedStatement deleteLabelStatement = connection.prepareStatement(Queries.DELETE_LABEL.getQuery());
                PreparedStatement deleteCoordinatesStatement = connection.prepareStatement(Queries.DELETE_COORDINATES.getQuery())
        ) {
            int creatorId = musicBand.getCreatorId();
            deleteMusicBandStatement.setInt(CREATOR_ID_POSITION_IN_DELETE_ALL, creatorId);
            deleteLabelStatement.setInt(CREATOR_ID_POSITION_IN_DELETE_ALL, creatorId);
            deleteCoordinatesStatement.setInt(COORDINATES_ID_POSITION, creatorId);
            deleteMusicBandStatement.executeUpdate();
            deleteLabelStatement.executeUpdate();
            deleteCoordinatesStatement.executeUpdate();
            return SUCCESSFUL_CODE;
        } catch (SQLException ex) {
            return ERROR;
        }

    }


    @Override
    public int deleteById(MusicBand musicBand) {
        final int musicBandCreatorIdPosition = 2;
        final int labelIdPosition = 1;
        final int coordinatesIdPosition = 1;
        try (
                PreparedStatement deleteMBById = connection.prepareStatement(Queries.DELETE_MUSICBAND_BY_ID.getQuery());
                PreparedStatement deleteLableById = connection.prepareStatement(Queries.DELETE_LABEL_BY_ID.getQuery());
                PreparedStatement deleteCoordinatesById = connection.prepareStatement(Queries.DELETE_COORDINATES_BY_ID.getQuery())
        ) {
            int cordId = findCoordinatesId(musicBand);
            int labelId = findLabelById(musicBand);
            if (cordId != ERROR && labelId != ERROR) {
                deleteMBById.setLong(MUSICBAND_ID_POSITION, musicBand.getId());
                deleteMBById.setInt(musicBandCreatorIdPosition, musicBand.getCreatorId());
                deleteLableById.setInt(labelIdPosition, labelId);
                deleteLableById.setInt(musicBandCreatorIdPosition, musicBand.getCreatorId());
                deleteCoordinatesById.setInt(coordinatesIdPosition, cordId);
                deleteCoordinatesById.setInt(musicBandCreatorIdPosition, musicBand.getCreatorId());
                deleteMBById.executeUpdate();
                deleteLableById.executeUpdate();
                deleteCoordinatesById.executeUpdate();
                return SUCCESSFUL_CODE;
            }
            return ERROR;
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    private int findCoordinatesId(MusicBand musicBand) {
        final int musicBandCreatorIdPosition = 2;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.FIND_COORDINATES_BY_MUSICBAND_ID.getQuery())) {
            preparedStatement.setLong(MUSICBAND_ID_POSITION, musicBand.getId());
            preparedStatement.setInt(musicBandCreatorIdPosition, musicBand.getCreatorId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("coordinates");
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    private int findLabelById(MusicBand musicBand) {
        final int musicBandCreatorIdPosition = 2;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.FIND_LABEL_BY_ID.getQuery())) {
            preparedStatement.setLong(MUSICBAND_ID_POSITION, musicBand.getId());
            preparedStatement.setInt(musicBandCreatorIdPosition, musicBand.getCreatorId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("label");
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    @Override
    public List<MusicBand> read() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.READ_MUSICBAND.getQuery())) {
            DateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US);
            ResultSet resultSet = preparedStatement.executeQuery();
            LinkedList<MusicBand> listOfMusicBandFromDB = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Coordinates coordinates = null;
                PreparedStatement coordinatesStatement = connection.prepareStatement(Queries.READ_COORDINATES_BY_MUSICBAND_ID.getQuery());
                coordinatesStatement.setInt(COORDINATES_ID_POSITION, id);
                ResultSet resultSet1 = coordinatesStatement.executeQuery();
                if (resultSet1.next()) {
                    coordinates = new Coordinates(resultSet1.getLong("x"), resultSet1.getFloat("y"));
                }
                resultSet1.next();
                LocalDateTime creationDate = LocalDateTime.parse(resultSet.getString("creation_date"));
                Integer numberOfParticipants = resultSet.getInt("number_of_participants");
                int albumsCount = resultSet.getInt("albums_count");
                String establishmentDateString = resultSet.getString("establishment_date");
                Date datestablishmentDate = format.parse(establishmentDateString);
                MusicGenre musicGenre = MusicGenre.valueOf((String) resultSet.getObject("genre"));
                Label label = null;
                PreparedStatement labelStatement = connection.prepareStatement(Queries.READ_LABEL_BY_ID.getQuery());
                labelStatement.setInt(LABEL_ID_POSITION, id);
                ResultSet resultSet2 = labelStatement.executeQuery();
                if (resultSet2.next()) {
                    label = new Label(resultSet2.getString("name"));
                }
                resultSet2.next();
                int creatorId = resultSet.getInt("creator_id");
                MusicBand musicBand = new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, albumsCount, datestablishmentDate, musicGenre, label, creatorId);
                listOfMusicBandFromDB.add(musicBand);
            }
            MainServerApp.LOGGER.info("Коллекция загружена из базы данных");
            return listOfMusicBandFromDB;
        } catch (SQLException e) {
            MainServerApp.LOGGER.info("Произошла ошибка, объекты с базы данных не загружены в коллекцию");
        } catch (ParseException e) {
            MainServerApp.LOGGER.info("Произошла ошибка с парсингом даты!");
        }
        return null;
    }
}
