package lk.ijse.gdse71.orm_course_work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionStaticsDto {
    private  String programName;
    private  Long totalSessions;
    private  Long completedSessions;
    private  Long pendingSessions;
}
