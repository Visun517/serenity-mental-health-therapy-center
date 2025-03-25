package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.Theraphist;

import java.sql.Date;
import java.util.List;

public interface TheraphistsDao extends CrudDao<Theraphist> {
    Theraphist getTherapist(String therapistId);
}
