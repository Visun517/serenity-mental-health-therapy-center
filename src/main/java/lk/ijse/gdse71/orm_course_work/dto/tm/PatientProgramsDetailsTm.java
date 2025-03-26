package lk.ijse.gdse71.orm_course_work.dto.tm;

import jakarta.persistence.*;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientProgramsDetailsTm {
    private String patient_id;
    private String program_id;
    private Patient patient;
    private TheraphyProgram theraphyProgram;
    private String status;
}
