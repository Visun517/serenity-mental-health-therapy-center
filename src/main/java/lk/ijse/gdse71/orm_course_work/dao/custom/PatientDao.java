package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.Patient;

public interface PatientDao extends CrudDao<Patient> {
    Patient getPatient(String patientId);
}
