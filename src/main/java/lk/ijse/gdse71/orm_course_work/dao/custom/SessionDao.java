package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;

import java.util.List;

public interface SessionDao extends CrudDao<TheraphySession> {
    boolean cancel(TheraphySession theraphySession);
    boolean reschedule(TheraphySession theraphySession);
    List<TheraphySession> getTherapistSchedule(String therapistId);
    TheraphySession getSession(String sessionId);
    String getSessionId(String patientId, String programId);
}
