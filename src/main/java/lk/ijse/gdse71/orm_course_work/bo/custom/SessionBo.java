package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.SchedulingConfiltException;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionStaticsDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;

import java.sql.SQLException;
import java.util.List;

public interface SessionBo extends SuperBo {
    String getNextId() throws SQLException;
    List<SessionDto> getAll() throws SQLException;
    boolean book(SessionDto sessionDto) throws SQLException, SchedulingConfiltException;
    boolean cancel(SessionDto sessionDto) throws SQLException, SchedulingConfiltException;
    boolean reschedule(SessionDto sessionDto) throws SQLException, SchedulingConfiltException;
    List<SessionDto> getTherapistSchedule(String therapistId);
    SessionDto getSession(String sessionId) throws SQLException;
    List<SessionStaticsDto> getSessionStatistics();
    String getPatinetProgramSession(String patientId, String programId) throws SQLException;
}
