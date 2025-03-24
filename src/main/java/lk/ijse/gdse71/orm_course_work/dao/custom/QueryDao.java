package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.SuperDao;
import lk.ijse.gdse71.orm_course_work.entity.Patient;

import java.util.List;

public interface QueryDao extends SuperDao {
    List<String> getPatientPrograms(String patientId);
    List<Patient> getFullEnrolledPatients();
}
