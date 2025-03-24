package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.PatientProgramsDetails;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;

import java.util.List;

public interface PatientProgrmasDao extends CrudDao<PatientProgramsDetails> {
    List<String> getPatientPrograms(String patientId);
    String getPatientStatus(PatinetProgramsDetailsIds patientId);
}
