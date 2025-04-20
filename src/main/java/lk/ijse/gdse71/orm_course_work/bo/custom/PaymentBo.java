package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.dto.PaymentDto;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBo extends SuperBo {
     String getNextId() throws SQLException;
     List<PaymentDto> getAll() throws SQLException;
     boolean save(PaymentDto dto) throws SQLException;
     boolean update(PaymentDto dto) throws SQLException;
     boolean delete(String id) throws SQLException;
     double getDuePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds);
     PaymentDto getPayment(String paymentId);
}
