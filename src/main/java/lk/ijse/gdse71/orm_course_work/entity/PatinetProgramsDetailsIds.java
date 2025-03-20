package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PatinetProgramsDetailsIds {
    private String patient_id;
    private String theraphy_pro_id;
}
