package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.DuplicateException;
import lk.ijse.gdse71.orm_course_work.bo.exception.MissingFieldException;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;

import java.sql.SQLException;
import java.util.List;

public interface ProgramsBo extends SuperBo {
     List<String> getAllPrograms() throws SQLException;
     String getNextId() throws SQLException;
     List<ProgramDto> getAll() throws SQLException;
     boolean save(ProgramDto programDto) throws SQLException, MissingFieldException, DuplicateException;
     boolean update(ProgramDto programDto) throws SQLException, MissingFieldException, DuplicateException;
     boolean delete(String id) throws SQLException;
     String getProgramName(String selectedItem);
     ProgramDto getProgram(String programId);
     double getTotalIncome() throws SQLException;
     List<String> getProgramsByPatientId(String patientId);
}
