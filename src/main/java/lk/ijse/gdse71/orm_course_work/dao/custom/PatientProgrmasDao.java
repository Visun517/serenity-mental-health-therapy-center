package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.PatientProgramsDetails;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;
import org.hibernate.Session;

import java.util.List;

public interface PatientProgrmasDao extends CrudDao<PatientProgramsDetails> {
    List<String> getPatientPrograms(String patientId);
    String getPatientStatus(PatinetProgramsDetailsIds patientId);
    PatientProgramsDetails getDuePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds);
    boolean reducePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds,double amount, Session session);
    boolean addUpPayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds, double amount, Session session);
}
