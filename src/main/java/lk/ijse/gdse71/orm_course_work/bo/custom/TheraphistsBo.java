package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.dto.TherapistDto;
import lk.ijse.gdse71.orm_course_work.dto.TherapistReportDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface TheraphistsBo extends SuperBo {
     String getNextId() throws SQLException;
     List<TherapistDto> getAll() throws SQLException;
     boolean saveTherapist(TherapistDto therapistDto ,List<String> programNames) throws SQLException;
     boolean delete(String id) throws SQLException;
     boolean update(TherapistDto therapistDto ,List<String> programNames) throws SQLException;
     List<TheraphyProgram> getAssigningPrograms(String therapistId) throws SQLException;
     List<TherapistDto> getAvailableTherapist(String programId, Time time, Date date);
    TherapistDto getTheraphist(String therapistId);
    List<TherapistReportDto> getTherapistPerformance();
}
