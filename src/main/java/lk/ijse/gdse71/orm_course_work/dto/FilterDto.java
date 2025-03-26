package lk.ijse.gdse71.orm_course_work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FilterDto {
    private String patient_id;
    private String name;
    private String contact;
    private String email;
    private String session_id;
    private Date date;
    private String program_id;
}
