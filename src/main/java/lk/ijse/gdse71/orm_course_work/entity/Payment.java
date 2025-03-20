package lk.ijse.gdse71.orm_course_work.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment {
    @Id
    private String payment_id;
    private Date date;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "theraphy_pro_id")
    private TheraphyProgram theraphyProgram;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private TheraphySession session;

    private String status;


}
