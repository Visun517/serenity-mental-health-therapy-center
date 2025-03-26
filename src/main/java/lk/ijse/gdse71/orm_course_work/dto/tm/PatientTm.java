package lk.ijse.gdse71.orm_course_work.dto.tm;

import lk.ijse.gdse71.orm_course_work.dto.PatientProgramsDetailsDto;
import lk.ijse.gdse71.orm_course_work.entity.PatientProgramsDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Driver;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientTm {
    private String patient_id;
    private String name;
    private String contact;
    private String email;
    private Date date;
    private String medical_history;
    private List<PatientProgramsDetailsDto> patientProgramsDetails;
}
