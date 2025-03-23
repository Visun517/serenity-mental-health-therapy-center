package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.sql.SQLException;
import java.util.List;

public interface TherapistsProgramsDao extends CrudDao<TheraphyProgram> {
    List<String> getAssigningPrograms(String id) throws SQLException;
}
