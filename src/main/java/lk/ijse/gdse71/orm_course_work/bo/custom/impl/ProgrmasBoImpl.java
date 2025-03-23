package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;

import java.sql.SQLException;
import java.util.List;

public class ProgrmasBoImpl implements ProgramsBo {
    private final ProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PROGRAM);

    @Override
    public List<String> getAllPrograms() throws SQLException {
        return progrmasDao.getAllPrograms();
    }
}
