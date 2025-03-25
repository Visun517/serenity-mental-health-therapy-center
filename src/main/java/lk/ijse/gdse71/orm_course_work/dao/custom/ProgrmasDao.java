package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.sql.SQLException;
import java.util.List;

public interface ProgrmasDao extends CrudDao<TheraphyProgram> {
    TheraphyProgram getProgramName(String selectedItem);
    List<String> getAllPrograms() throws SQLException;
    TheraphyProgram getNameFromProgram(String programName) throws SQLException;
    TheraphyProgram getIdFromProgram(String programId) throws SQLException;
}
