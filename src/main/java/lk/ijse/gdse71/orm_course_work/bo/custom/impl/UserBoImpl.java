package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.UserBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.UserDao;
import lk.ijse.gdse71.orm_course_work.entity.User;

public class UserBoImpl implements UserBo {
    private final UserDao userDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.USER);

    @Override
    public User getUser(String userName) {
        return userDao.getUser(userName);
    }
}
