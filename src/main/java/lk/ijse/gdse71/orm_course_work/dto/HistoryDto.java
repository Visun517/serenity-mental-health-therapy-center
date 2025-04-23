package lk.ijse.gdse71.orm_course_work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryDto {
    private String patient_id;
    private String programId;
    private String session_id;
    private String totalFee;
    private String paidAmount;
    private String pendingAmount;
    private String status;
}
