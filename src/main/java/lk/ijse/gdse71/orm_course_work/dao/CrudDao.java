package lk.ijse.gdse71.orm_course_work.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDao<T> extends SuperDao{
    String getNextId() throws SQLException;
    List<T> getAll() throws SQLException;
    boolean save(T dto) throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(String id) throws SQLException;
}
