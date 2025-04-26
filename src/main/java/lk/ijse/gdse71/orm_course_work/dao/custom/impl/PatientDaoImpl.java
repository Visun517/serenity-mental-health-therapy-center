package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.PatientDao;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("select p.patient_id from Patient p order by p.patient_id desc", String.class).setMaxResults(1).uniqueResult();
        return lastId;
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        List<Patient> patients = session.createQuery("from Patient", Patient.class).list();
        ArrayList<Patient> patientArrayList = new ArrayList<>();
        patientArrayList.addAll(patients);

        return patientArrayList;
    }

    @Override
    public boolean save(Patient patient) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(patient);
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
    public boolean update(Patient patient) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(patient);
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

        try {
            Query paymentQuery = session.createQuery("DELETE FROM Payment p WHERE p.patient.patient_id = :id");
            paymentQuery.setParameter("id", id);
            paymentQuery.executeUpdate();

            Query sessionQuery = session.createQuery("DELETE FROM TheraphySession ts WHERE ts.patient.patient_id = :id");
            sessionQuery.setParameter("id", id);
            sessionQuery.executeUpdate();

            Query ppdQuery = session.createQuery("DELETE FROM PatientProgramsDetails ppd WHERE ppd.patient.patient_id = :id");
            ppdQuery.setParameter("id", id);
            ppdQuery.executeUpdate();

            Patient patient = session.get(Patient.class, id);

            if (patient != null) {
                session.delete(patient);
                transaction.commit();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
        return false;
    }



    @Override
    public Patient getPatient(String patientId) {
        Session session = factoryConfiguration.getSession();
        Patient patient = session.get(Patient.class, patientId);
        session.close();
        return patient;
    }
}
