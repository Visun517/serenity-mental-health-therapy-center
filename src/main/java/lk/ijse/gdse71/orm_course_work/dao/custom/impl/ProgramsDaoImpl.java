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
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("select t.theraphy_pro_id from TheraphyProgram t order by t.theraphy_pro_id desc", String.class).setMaxResults(1).uniqueResult();
        return lastId;
    }

    @Override
    public List<TheraphyProgram> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        List<TheraphyProgram> theraphyPrograms = session.createQuery("from TheraphyProgram", TheraphyProgram.class).list();
        ArrayList<TheraphyProgram> theraphyProgramArrayList = new ArrayList<>();
        theraphyProgramArrayList.addAll(theraphyPrograms);

        return theraphyProgramArrayList;
    }

    @Override
    public boolean save(TheraphyProgram theraphyProgram) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(theraphyProgram);
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
    public boolean update(TheraphyProgram theraphyProgram) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(theraphyProgram);
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
    public boolean delete(String id) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            TheraphyProgram theraphyProgram = session.get(TheraphyProgram.class, id);
            if (theraphyProgram != null){
                session.remove(theraphyProgram);
                transaction.commit();
                return true;
            }else{
                System.out.println("TheraphyProgram is null");
                return false;
            }


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
    public TheraphyProgram getProgramName(String selectedItem) {
        Session session = factoryConfiguration.getSession();
        TheraphyProgram theraphyProgram = session.get(TheraphyProgram.class, selectedItem);
        session.close();
        return theraphyProgram;
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

    @Override
    public TheraphyProgram getProgram(String programId) {
        Session session = factoryConfiguration.getSession();
        TheraphyProgram theraphyProgram = session.get(TheraphyProgram.class, programId);
        session.close();
        return theraphyProgram;
    }
}
