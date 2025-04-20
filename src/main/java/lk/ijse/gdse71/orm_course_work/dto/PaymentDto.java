package lk.ijse.gdse71.orm_course_work.dto;

import jakarta.persistence.Entity;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    private String payment_id;
    private Date date;
    private double amount;
    private String patientId;
    private String theraphyProgramId;
    private String sessionId;
    private String status;
}
