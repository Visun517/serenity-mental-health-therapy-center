package lk.ijse.gdse71.orm_course_work.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramTm {
    private String theraphy_pro_id;
    private String name;
    private String duration;
    private double fee;
    private String description;
}
