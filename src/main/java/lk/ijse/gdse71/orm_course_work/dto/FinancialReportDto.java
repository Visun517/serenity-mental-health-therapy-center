package lk.ijse.gdse71.orm_course_work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialReportDto {
    private String payment_id;
    private String patinet_id;
    private double amount;
    private String status;
    private Date date;
}
