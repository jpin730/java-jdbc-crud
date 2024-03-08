package org.example.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    List<T> findAll() throws SQLException;

    T getById(Integer id) throws SQLException;

    void save(T t);

    void update(T t);

    void delete(Integer id);
}
