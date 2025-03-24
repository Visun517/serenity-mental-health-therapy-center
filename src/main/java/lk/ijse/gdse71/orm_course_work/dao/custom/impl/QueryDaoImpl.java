package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.QueryDao;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class QueryDaoImpl implements QueryDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

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
    public List<Patient> getFullEnrolledPatients() {
        return List.of();
    }

}
