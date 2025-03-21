package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public interface UserBo extends SuperBo {
    public User getUser(String userName);
}
