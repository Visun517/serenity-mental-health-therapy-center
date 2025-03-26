package lk.ijse.gdse71.orm_course_work.dto;

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
public class PatientProgramsDetailsDto {
    private String patient_id;
    private String program_id;
    private Patient patient;
    private TheraphyProgram theraphyProgram;
    private String status;
}
