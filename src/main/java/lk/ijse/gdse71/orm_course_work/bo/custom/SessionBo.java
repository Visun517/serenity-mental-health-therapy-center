package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;

import java.sql.SQLException;
import java.util.List;

public interface SessionBo extends SuperBo {
    String getNextId() throws SQLException;
    List<SessionDto> getAll() throws SQLException;
    boolean book(SessionDto sessionDto) throws SQLException;
    boolean cancel(SessionDto sessionDto)throws SQLException;
    boolean reschedule(SessionDto sessionDto) throws SQLException;
}
