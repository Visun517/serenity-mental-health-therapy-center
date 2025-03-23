package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
import lk.ijse.gdse71.orm_course_work.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramsDaoImpl implements ProgrmasDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public List<TheraphyProgram> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public boolean save(TheraphyProgram dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(TheraphyProgram dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<String> getAllPrograms() throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            List<String> programs = session.createQuery(" select t.name from TheraphyProgram t", String.class).list();
            transaction.commit();
            return programs;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public TheraphyProgram getNameFromProgram(String programName) throws SQLException {
        Session session = factoryConfiguration.getSession();
        try {
            String sql = "from TheraphyProgram t where t.name = :programName";
            Query<TheraphyProgram> query = session.createQuery(sql, TheraphyProgram.class);
            query.setParameter("programName",programName);
            return query.uniqueResult();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (session != null){
                session.close();
            }
        }

    }

    @Override
    public TheraphyProgram getIdFromProgram(String programId) throws SQLException {
        Session session = factoryConfiguration.getSession();
        TheraphyProgram theraphyProgram = session.get(TheraphyProgram.class, programId);
        session.close();
        return theraphyProgram;
    }
}
