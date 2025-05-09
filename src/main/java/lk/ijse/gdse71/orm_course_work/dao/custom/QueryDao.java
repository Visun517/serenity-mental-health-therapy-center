package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.SuperDao;
import lk.ijse.gdse71.orm_course_work.dto.FilterDto;
import lk.ijse.gdse71.orm_course_work.dto.FinancialReportDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionStaticsDto;
import lk.ijse.gdse71.orm_course_work.dto.TherapistReportDto;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.Theraphist;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface QueryDao extends SuperDao {
    List<String> getPatientPrograms(String patientId);
    List<Patient> getFullEnrolledPatients();
    List<Theraphist> getAvailableTherapist(String programId, Time time, Date date);
    List<FilterDto> filterByStatus(String status);
    List<FilterDto> filterByProgramId(String programId);
    List<TherapistReportDto> getTherapistPerformance();
    List<SessionStaticsDto> getSessionStatistics();
    List<FinancialReportDto> getDailyRevenue(String currentDate);
}
