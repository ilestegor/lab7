package server.utility;

public enum Queries {
    CREATE_USER("insert into creator (login, password, salt) values (?, ?, ?) returning id;"),


    DELETE_USER("deleteAll from creator where id = ?"),
    READ_USER("select * from creator"),
    GET_SALT("select salt from creator where login = ?"),
    CREATE_MUSICBAND("insert into musicband " +
            "(name, coordinates, creation_date, number_of_participants, albums_count, establishment_date, genre, label, creator_id)" +
            "values (?, ?, ?, ?, ?, ?, cast(? as music_genre), ?, ?) returning id;"),
    CREATE_MUSICBAND_COORDINATES("insert into coordinates (x, y, creator_id) values (?, ?, ?) returning id;"),
    CREATE_MUSICBAND_LABEL("insert into label (name, creator_id) values (?, ?) returning id;"),
    READ_MUSICBAND("select * from musicband"),
    READ_COORDINATES_BY_MUSICBAND_ID("select x, y from coordinates join musicband m on coordinates.id = m.coordinates where  m.id = ?"),
    READ_LABEL_BY_ID("select label.name from label join musicband m on label.id = m.label where label.id = ?"),
    DELETE_MUSICBAND("delete from musicband where creator_id = ?"),
    DELETE_LABEL("delete from label where creator_id = ?"),
    DELETE_COORDINATES("delete from coordinates where creator_id = ?"),
    DELETE_MUSICBAND_BY_ID("delete from musicband where id = ? and creator_id = ?"),
    FIND_COORDINATES_BY_MUSICBAND_ID("select musicband.coordinates from musicband where id = ? and creator_id = ?"),
    FIND_LABEL_BY_ID("select musicband.label from musicband where id = ? and creator_id = ?"),
    DELETE_LABEL_BY_ID("delete from label where id = ? and creator_id = ?"),
    DELETE_COORDINATES_BY_ID("delete from coordinates where id = ? and creator_id = ?"),
    UPDATE_USER("update creator set login = ?, password = ? where id = ?"),
    UPDATE_MUSICBAND("update musicband set name = ?, number_of_participants = ?, albums_count = ?, establishment_date = ?, genre = cast(? as music_genre) where id = ? and creator_id = ?"),
    UPDATE_COORDINATES("update coordinates set x = ?, y = ? where id = ? and creator_id = ?"),
    UPDATE_LABEL("update label set name = ? where id = ? and creator_id = ?"),
    GET_USER_LOGIN_BY_ID("select login from creator where id = ?"),
    CREATE_CREATOR_TABLE("create table if not exists creator ( id  serial primary key, login text not null, password text not null, salt text not null);"),
    CREATE_LABEL_TABLE("create table if not exists label (id serial primary key, name text, creator_id integer);"),
    CREATE_COORDINATES_TABLE("create table if not exists coordinates (id serial primary key, x bigint not null constraint coordinates_x_check check ( x > '-611'::integer ), y double precision not null constraint coordinates_y_check check ( y <= (505)::double precision ), creator_id integer);"),
    CREATE_MUSICBAND_TABLE("create table if not exists musicband (id serial primary key, name text not null constraint musicband_name_check check (name <> ''::text), coordinates serial references coordinates, creation_date text not null, number_of_participants integer not null constraint musicband_number_of_participants_check check (number_of_participants > 0), albums_count integer not null constraint musicband_albums_count_check check (albums_count > 0), establishment_date text not null, genre music_genre not null, label serial references label, creator_id serial references creator);");

    private final String query;

    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
