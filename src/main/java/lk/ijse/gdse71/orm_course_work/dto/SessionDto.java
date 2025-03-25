package lk.ijse.gdse71.orm_course_work.dto;

import jakarta.persistence.*;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.Payment;
import lk.ijse.gdse71.orm_course_work.entity.Theraphist;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDto {
    private String session_id;
    private Date date;
    private Time time;
    private String status;
    private String patient_id;
    private String therapist_id;
    private String program_id;
}
