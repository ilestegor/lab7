package server.dao;

import java.util.List;

public interface DAO<T> {
    int ERROR = -1;

    int create(T t);

    int update(T t);


    int deleteAll(T t);

    int deleteById(T t);

    List<T> read();
}
