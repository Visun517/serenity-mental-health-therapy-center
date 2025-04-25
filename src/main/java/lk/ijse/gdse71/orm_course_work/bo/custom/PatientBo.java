package lk.ijse.gdse71.orm_course_work.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.RegistrationException;
import lk.ijse.gdse71.orm_course_work.dto.FilterDto;
import lk.ijse.gdse71.orm_course_work.dto.PatientDto;
import lk.ijse.gdse71.orm_course_work.dto.PatientProgramsDetailsDto;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface PatientBo extends SuperBo {
    String getNextId() throws SQLException;
    List<PatientDto> getAll() throws SQLException;
    List<TheraphyProgram> getPatientPrograms(String patientId) throws SQLException;
    String getPatinetStatus(PatinetProgramsDetailsIds patientId);
    boolean patinetSave(PatientDto patientDto, String status, ObservableList<String> programNames) throws SQLException, RegistrationException;
    boolean update(PatientDto patientDto, String status, ObservableList<String> programNames) throws SQLException, RegistrationException;
    boolean delete(String text)throws SQLException;
    List<PatientDto> getFullEnrolledPatients();
    List<FilterDto> filterByStatus(String status);
    List<FilterDto> filterByProgramId(String programId);
    PatientDto getPatinet(String patientId);
    List<PatientProgramsDetailsDto> getTheraphyHistoryById(String patientId) throws SQLException;
}
