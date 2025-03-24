package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PatinetProgramsDetailsIds {
    private String patient_id;
    private String theraphy_pro_id;
}
