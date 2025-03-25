package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.SessionDao;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionDaoImpl implements SessionDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("select s.session_id from TheraphySession s order by s.session_id desc", String.class).setMaxResults(1).uniqueResult();
        return lastId;
    }

    @Override
    public List<TheraphySession> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        List<TheraphySession> theraphySessions = session.createQuery("from TheraphySession", TheraphySession.class).list();
        ArrayList<TheraphySession> theraphySessionArrayList = new ArrayList<>();
        theraphySessionArrayList.addAll(theraphySessions);

        return theraphySessionArrayList;
    }

    @Override
    public boolean save(TheraphySession theraphySession) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(theraphySession);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public boolean cancel(TheraphySession theraphySession) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(theraphySession);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public boolean reschedule(TheraphySession theraphySession) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(theraphySession);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }
    @Override
    public boolean update(TheraphySession dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

}
