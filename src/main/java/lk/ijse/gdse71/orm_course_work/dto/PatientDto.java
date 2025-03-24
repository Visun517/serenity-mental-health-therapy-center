package lk.ijse.gdse71.orm_course_work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDto {
    private String patient_id;
    private String name;
    private String contact;
    private String email;
    private Date date;
    private String medical_history;

}
