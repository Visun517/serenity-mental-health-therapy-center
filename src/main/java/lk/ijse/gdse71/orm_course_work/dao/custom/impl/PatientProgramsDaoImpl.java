package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.PatientProgrmasDao;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.PatientProgramsDetails;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientProgramsDaoImpl implements PatientProgrmasDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public List<PatientProgramsDetails> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        return session.createQuery("from PatientProgramsDetails ", PatientProgramsDetails.class).list();
    }

    @Override
    public boolean save(PatientProgramsDetails dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PatientProgramsDetails dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<String> getPatientPrograms(String patientId) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT pp.theraphyProgram.theraphy_pro_id FROM Patient p JOIN p.patientProgramsDetails pp  WHERE p.patient_id = :patientId",
                    String.class
            );
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public String getPatientStatus(PatinetProgramsDetailsIds patientId) {
        Session session = factoryConfiguration.getSession();
        PatientProgramsDetails patientProgramsDetails = session.get(PatientProgramsDetails.class, patientId);
        session.close();
        return patientProgramsDetails.getStatus();
    }

    @Override
    public PatientProgramsDetails getDuePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds) {
        Session session = factoryConfiguration.getSession();
        PatientProgramsDetails patientProgramsDetails = session.get(PatientProgramsDetails.class, patinetProgramsDetailsIds);
        return patientProgramsDetails;
    }

    @Override
    public boolean reducePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds, double amount, Session session) {
        try {
            PatientProgramsDetails patientProgramsDetails = session.get(PatientProgramsDetails.class, patinetProgramsDetailsIds);
            if (patientProgramsDetails != null) {
                double tableAmount = patientProgramsDetails.getProgramAmount();
                double tableSaveAmount = tableAmount - amount;

                patientProgramsDetails.setProgramAmount(tableSaveAmount);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUpPayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds, double amount, Session session) {
        try {
            PatientProgramsDetails patientProgramsDetails = session.get(PatientProgramsDetails.class, patinetProgramsDetailsIds);
            if (patientProgramsDetails != null) {
                double tableAmount = patientProgramsDetails.getProgramAmount();
                double tableSaveAmount = tableAmount + amount;

                patientProgramsDetails.setProgramAmount(tableSaveAmount);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getProgramsByPatientId(String patientId) {
        Session session = factoryConfiguration.getSession();
        String hql = "SELECT p.theraphyProgram.theraphy_pro_id FROM Patient pt JOIN pt.patientProgramsDetails p WHERE pt.patient_id = :patientId";
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }
}

