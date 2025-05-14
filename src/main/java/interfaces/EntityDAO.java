package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface EntityDAO<T> {
    T create(T entity) throws SQLException;

    T get(long id) throws SQLException;

    T update(T entity) throws SQLException;

    T deleteById(long id) throws SQLException;

    List<T> getAll() throws SQLException;
}
