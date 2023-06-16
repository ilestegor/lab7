package server.dao;

import server.MainServerApp;
import server.utility.Queries;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreatorManager {
    private final Connection connection;

    public TableCreatorManager(Connection connection) {
        this.connection = connection;
        createCreator();
        createLabelTable();
        createCoordinatesTable();
        createMusicBandTable();
    }

    private void createCreator() {
        try (
                Statement createCreator = connection.createStatement()
        ) {
            createCreator.execute(Queries.CREATE_CREATOR_TABLE.getQuery());
        } catch (SQLException ex) {
            MainServerApp.LOGGER.info("Произошла ошибка при создании таблицы пользователей");
        }
    }

    private void createLabelTable() {
        try
                (Statement createLabelTable = connection.createStatement()) {
            createLabelTable.execute(Queries.CREATE_LABEL_TABLE.getQuery());
        } catch (SQLException ex) {
            MainServerApp.LOGGER.info("Произошла ошибка при создании таблицы Label");
        }
    }

    private void createCoordinatesTable() {
        try (Statement createCoordinatesTable = connection.createStatement()) {
            createCoordinatesTable.execute(Queries.CREATE_COORDINATES_TABLE.getQuery());
        } catch (SQLException ex) {
            MainServerApp.LOGGER.info("Произошла ошибка при создании таблицы Coordinates");
        }
    }

    private void createMusicBandTable() {
        try (
                Statement createMusicBandTable = connection.createStatement()
        ) {
            createMusicBandTable.execute(Queries.CREATE_MUSICBAND_TABLE.getQuery());
        } catch (SQLException ex) {
            MainServerApp.LOGGER.info("Произошла ошибка при создании таблицы MusicBand");
        }
    }
}
