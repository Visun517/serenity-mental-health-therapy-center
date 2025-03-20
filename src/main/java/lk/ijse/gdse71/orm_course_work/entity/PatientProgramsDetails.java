package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patient_programs_details")
public class PatientProgramsDetails {
    @EmbeddedId
    private PatinetProgramsDetailsIds ids;

    @ManyToOne
    @MapsId("patient_id")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("theraphy_pro_id")
    @JoinColumn(name = "theraphy_pro_id")
    private TheraphyProgram theraphyProgram;

    private String status;
}
