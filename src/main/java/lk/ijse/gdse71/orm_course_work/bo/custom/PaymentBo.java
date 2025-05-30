package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.PaymentProcessingException;
import lk.ijse.gdse71.orm_course_work.dto.FinancialReportDto;
import lk.ijse.gdse71.orm_course_work.dto.PaymentDto;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentBo extends SuperBo {
     String getNextId() throws SQLException;
     List<PaymentDto> getAll() throws SQLException;
     boolean save(PaymentDto dto) throws SQLException, PaymentProcessingException;
     boolean update(PaymentDto dto) throws SQLException, PaymentProcessingException;
     boolean delete(String id) throws SQLException;
     double getDuePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds);
     PaymentDto getPayment(String paymentId);
     List<FinancialReportDto> getDailyRevenue(String currentDate);
     double getReceivedPayment() throws SQLException;
     double getPendingPayment() throws SQLException;
}
