package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;

public interface SessionDao extends CrudDao<TheraphySession> {
    boolean cancel(TheraphySession theraphySession);
    boolean reschedule(TheraphySession theraphySession);
}
